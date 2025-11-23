import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
//dominio
enum StatusCandidato { PENDENTE, APROVADO, REJEITADO }

final class Candidato {
    final UUID id;
    String nome;
    String email;
    StatusCandidato status;
    boolean pendente; // usado para listar “pendentes”
    Candidato(UUID id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.status = StatusCandidato.PENDENTE;
        this.pendente = true;
    }
    @Override public String toString() {
        return "Candidato{id=%s, nome=%s, email=%s, status=%s, pendente=%s}"
                .formatted(id, nome, email, status, pendente);
    }
}
final class Afiliacao {
    final UUID id = UUID.randomUUID();
    final UUID candidatoId;
    final LocalDateTime dataCriacao = LocalDateTime.now();
    Afiliacao(UUID candidatoId) { this.candidatoId = candidatoId; }

    @Override public String toString() {
        return "Afiliacao{id=%s, candidatoId=%s, data=%s}"
                .formatted(id, candidatoId, dataCriacao);
    }
}
final class Aceite {
    final UUID id = UUID.randomUUID();
    final UUID adminId;
    final UUID candidatoId;
    final LocalDateTime dataAceite = LocalDateTime.now();
    Aceite(UUID adminId, UUID candidatoId) {
        this.adminId = adminId;
        this.candidatoId = candidatoId;
    }
    @Override public String toString() {
        return "Aceite{id=%s, adminId=%s, candidatoId=%s, data=%s}"
                .formatted(id, adminId, candidatoId, dataAceite);
    }
}
final class Rejeicao {
    final UUID id = UUID.randomUUID();
    final UUID candidatoId;
    final UUID adminId;
    final String motivo;
    final LocalDateTime data = LocalDateTime.now();
    Rejeicao(UUID candidatoId, UUID adminId, String motivo) {
        this.candidatoId = candidatoId;
        this.adminId = adminId;
        this.motivo = motivo;
    }
    @Override public String toString() {
        return "Rejeicao{id=%s, cand=%s, admin=%s, motivo='%s', data=%s}"
                .formatted(id, candidatoId, adminId, motivo, data);
    }
}
//"repositorios"
interface CandidatoRepository {
    Optional<Candidato> findById(UUID id);
    List<Candidato> findPendentes();
    void save(Candidato c);
}
final class InMemoryCandidatoRepository implements CandidatoRepository {
    private final Map<UUID, Candidato> db = new ConcurrentHashMap<>();
    @Override public Optional<Candidato> findById(UUID id) { return Optional.ofNullable(db.get(id)); }
    @Override public List<Candidato> findPendentes() {
        return db.values().stream().filter(c -> c.pendente).toList();
    }
    @Override public void save(Candidato c) { db.put(c.id, c); }
}
interface AfiliacaoRepository {
    void save(Afiliacao a);
    List<Afiliacao> findAll();
}
final class InMemoryAfiliacaoRepository implements AfiliacaoRepository {
    private final List<Afiliacao> db = Collections.synchronizedList(new ArrayList<>());
    @Override public void save(Afiliacao a) { db.add(a); }
    @Override public List<Afiliacao> findAll() { return List.copyOf(db); }
}
interface AceiteRepository {
    void save(Aceite a);
    List<Aceite> findAll();
}
final class InMemoryAceiteRepository implements AceiteRepository {
    private final List<Aceite> db = Collections.synchronizedList(new ArrayList<>());
    @Override public void save(Aceite a) { db.add(a); }
    @Override public List<Aceite> findAll() { return List.copyOf(db); }
}
interface RejeicaoRepository {
    void save(Rejeicao r);
    List<Rejeicao> findAll();
}
final class InMemoryRejeicaoRepository implements RejeicaoRepository {
    private final List<Rejeicao> db = new CopyOnWriteArrayList<>();
    public void save(Rejeicao r) { db.add(r); }
    public List<Rejeicao> findAll() { return List.copyOf(db); }
}
//servicos
final class CandidatoService {
    private final CandidatoRepository repo;
    CandidatoService(CandidatoRepository repo) { this.repo = repo; }
    public List<Candidato> obterPendentes() { return repo.findPendentes(); }
    public Candidato carregar(UUID candidatoId) {
        return repo.findById(candidatoId)
                .orElseThrow(() -> new NoSuchElementException("Candidato não encontrado: " + candidatoId));
    }
    public void atualizarStatus(UUID candidatoId, StatusCandidato status) {
        var c = carregar(candidatoId);
        c.status = status;
        repo.save(c);
    }
    public void removerPendencia(UUID candidatoId) {
        var c = carregar(candidatoId);
        c.pendente = false;
        repo.save(c);
    }
    public void rejeitar(UUID candidatoId) {
        var c = carregar(candidatoId);
        c.status = StatusCandidato.REJEITADO;
        c.pendente = false;
        repo.save(c);
    }
}
final class AfiliacaoService {
    private final AfiliacaoRepository repo;
    AfiliacaoService(AfiliacaoRepository repo) { this.repo = repo; }
    public Afiliacao criarAfiliacao(Candidato c) {
        Afiliacao a = new Afiliacao(c.id);
        repo.save(a);
        return a;
    }
}
final class AceiteService {
    private final AceiteRepository repo;
    AceiteService(AceiteRepository repo) { this.repo = repo; }
    public Aceite registrarAceite(UUID adminId, Candidato candidato) {
        Aceite ac = new Aceite(adminId, candidato.id);
        repo.save(ac);
        return ac;
    }
}
final class RejeicaoService {
    private final RejeicaoRepository repo;
    RejeicaoService(RejeicaoRepository repo) { this.repo = repo; }
    public Rejeicao registrar(UUID adminId, UUID candidatoId, String motivo) {
        Rejeicao r = new Rejeicao(candidatoId, adminId, motivo);
        repo.save(r);
        return r;
    }
}
final class EmailService {
    public boolean enviarAprovacao(String destinatarioEmail, Afiliacao afiliacao) {
        System.out.printf("[EmailService] Enviado para %s: Afiliação %s criada em %s%n",
                destinatarioEmail, afiliacao.id, afiliacao.dataCriacao);
        return true;
    }
    public boolean enviarRejeicao(String destinatarioEmail, String motivo) {
        System.out.printf("[EmailService] Rejeicao para %s: motivo='%s'%n",
                destinatarioEmail, motivo);
        return true;
    }
}
//controller
final class ControladorAprovacao {
    private final CandidatoService candidatoService;
    private final AfiliacaoService afiliacaoService;
    private final AceiteService aceiteService;
    private final EmailService emailService;
    private final RejeicaoService rejeicaoService;
    ControladorAprovacao(CandidatoService c, AfiliacaoService a, AceiteService ac,
                         EmailService e, RejeicaoService r) {
        this.candidatoService = c;
        this.afiliacaoService = a;
        this.aceiteService = ac;
        this.emailService = e;
        this.rejeicaoService = r;
    }
    public List<Candidato> listarPendentes() {
        return candidatoService.obterPendentes();
    }
    public void aprovarCandidato(UUID candidatoId, UUID adminId) {
        var candidato = candidatoService.carregar(candidatoId);
        var afiliacao = afiliacaoService.criarAfiliacao(candidato);
        var aceite = aceiteService.registrarAceite(adminId, candidato);
        candidatoService.atualizarStatus(candidato.id, StatusCandidato.APROVADO);
        candidatoService.removerPendencia(candidato.id);
        var emailOK = emailService.enviarAprovacao(candidato.email, afiliacao);

        System.out.printf(
            "[Controller] Aprovacao concluida. candidato=%s, afiliacao=%s, aceite=%s, emailOK=%s%n",
            candidato.id, afiliacao.id, aceite.id, emailOK
        );
    }
    public void reprovarCandidato(UUID candidatoId, UUID adminId, String motivo) {
        var candidato = candidatoService.carregar(candidatoId);
        var rejeicao = rejeicaoService.registrar(adminId, candidatoId, motivo);
        candidatoService.rejeitar(candidatoId);
        var emailOK = emailService.enviarRejeicao(candidato.email, motivo);
        System.out.printf(
            "[Controller] Reprovacao concluida. candidato=%s, rejeicao=%s, emailOK=%s%n",
            candidato.id, rejeicao.id, emailOK
        );
    }
}
//simula o representante em acao
public class Main {
    public static void main(String[] args) {
        // repositorio
        var candRepo = new InMemoryCandidatoRepository();
        var afiRepo  = new InMemoryAfiliacaoRepository();
        var aceRepo  = new InMemoryAceiteRepository();
        var rejRepo  = new InMemoryRejeicaoRepository();
        // servicos
        var candSvc = new CandidatoService(candRepo);
        var afiSvc  = new AfiliacaoService(afiRepo);
        var aceSvc  = new AceiteService(aceRepo);
        var rejSvc  = new RejeicaoService(rejRepo);
        var email   = new EmailService();
        // controller
        var ctrl = new ControladorAprovacao(candSvc, afiSvc, aceSvc, email, rejSvc);
        //simula 2 candidatos pendentes
        var c1 = new Candidato(UUID.randomUUID(), "Ana", "ana@exemplo.com");
        var c2 = new Candidato(UUID.randomUUID(), "Bruno", "bruno@exemplo.com");
        candRepo.save(c1);
        candRepo.save(c2);
        System.out.println("Pendentes (inicio): " + ctrl.listarPendentes());
        UUID adminId = UUID.randomUUID();

        //Aprovar primeiro danditato
        ctrl.aprovarCandidato(c1.id, adminId);

        //Reprovar segundo candidato com o motivo
        ctrl.reprovarCandidato(c2.id, adminId, "você não tem o perfil que procuramos");

        System.out.println("Pendentes (fim): " + ctrl.listarPendentes());
    }
}
