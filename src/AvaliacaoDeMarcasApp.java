import java.util.*;

// Classe para representar uma resposta a uma avaliação
class Resposta {
    private String usuario;
    private String texto;

    public Resposta(String usuario, String texto) {
        this.usuario = usuario;
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void mostrarResposta() {
        System.out.println("Resposta de " + usuario + ": " + texto);
    }
}

// Classe para representar uma avaliação
class Avaliacao {
    private String usuario;
    private String comentario;
    private int nota;
    private List<Resposta> respostas;

    public Avaliacao(String usuario, String comentario, int nota) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.nota = nota;
        this.respostas = new ArrayList<>();
    }

    public int getNota() {
        return nota;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void adicionarResposta(String usuario, String texto) {
        respostas.add(new Resposta(usuario, texto));
    }

    public void mostrarAvaliacao() {
        System.out.println("Usuário: " + usuario);
        System.out.println("Nota: " + nota + " | Comentário: " + comentario);
    }
}

// Classe para representar uma marca
class Marca {
    private String nome;
    private String categoria;
    private Date dataCadastro;
    private List<Avaliacao> avaliacoes;
    private Set<String> selos;

    public Marca(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
        this.dataCadastro = new Date();
        this.avaliacoes = new ArrayList<>();
        this.selos = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void adicionarAvaliacao(String usuario, String comentario, int nota) {
        if (nota >= 1 && nota <= 5) {
            avaliacoes.add(new Avaliacao(usuario, comentario, nota));
        } else {
            System.out.println("Nota inválida. Use valores entre 1 e 5.");
        }
    }

    public double calcularIndiceConfianca() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        int soma = 0;
        for (Avaliacao avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }
        return (double) soma / avaliacoes.size();
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void adicionarSelo(String selo) {
        if (isSeloValido(selo)) {
            selos.add(selo);
            System.out.println("Selo " + selo + " atribuído à marca " + nome);
        } else {
            System.out.println("Selo inválido.");
        }
    }

    public void removerSelo(String selo) {
        if (selos.contains(selo)) {
            selos.remove(selo);
            System.out.println("Selo " + selo + " removido da marca " + nome);
        } else {
            System.out.println("Marca não tem o selo " + selo);
        }
    }

    private boolean isSeloValido(String selo) {
        return selo.equalsIgnoreCase("Sustentável") || selo.equalsIgnoreCase("Qualidade")
                || selo.equalsIgnoreCase("Atendimento") || selo.equalsIgnoreCase("Estilo de Vida");
    }

    public void mostrarDetalhes() {
        System.out.println("Marca: " + nome);
        System.out.println("Categoria: " + categoria);
        System.out.println("Índice de Confiança: " + String.format("%.2f", calcularIndiceConfianca()));
        System.out.println("Avaliações:");
        for (Avaliacao avaliacao : avaliacoes) {
            avaliacao.mostrarAvaliacao();
        }
        System.out.println("Selos:");
        if (!selos.isEmpty()) {
            selos.forEach(selo -> System.out.println("- " + selo));
        } else {
            System.out.println("Nenhum selo atribuído.");
        }
    }
}

// Classe para gerenciamento de usuários
class GerenciadorUsuarios {
    private List<Usuario> usuarios;
    private Usuario usuarioLogado;

    public GerenciadorUsuarios() {
        usuarios = new ArrayList<>();
        usuarioLogado = null;
    }

    public void registrarUsuario(String nome, String email, String senha) {
        if (buscarUsuarioPorEmail(email) != null) {
            System.out.println("Este e-mail já está registrado.");
            return;
        }
        Usuario novoUsuario = new Usuario(nome, email, senha);
        usuarios.add(novoUsuario);
        System.out.println("Usuário registrado com sucesso!");
    }

    public void login(String email, String senha) {
        Usuario usuario = buscarUsuarioPorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuarioLogado = usuario;
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("E-mail ou senha incorretos.");
        }
    }

    public void logout() {
        if (usuarioLogado != null) {
            System.out.println("Logout realizado com sucesso.");
            usuarioLogado = null;
        } else {
            System.out.println("Nenhum usuário logado.");
        }
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }
}

// Classe para representar um usuário
class Usuario {
    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}

// Classe principal
public class AvaliacaoDeMarcasApp {
    private static List<Marca> marcas = new ArrayList<>();
    private static GerenciadorUsuarios gerenciadorUsuarios = new GerenciadorUsuarios();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            if (gerenciadorUsuarios.getUsuarioLogado() == null) {
                exibirMenu();
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (opcao) {
                    case 1:
                        criarContaOuLogin();
                        break;
                    case 2:
                        listarMarcas();
                        break;
                    case 3:
                        System.out.println("Saindo do aplicativo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                exibirMenuAposLogin();
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (opcao) {
                    case 1:
                        cadastrarMarca();
                        break;
                    case 2:
                        avaliarMarca();
                        break;
                    case 3:
                        gerenciadorUsuarios.logout();
                        break;
                    case 4:
                        buscarMarcas();
                        break;
                    case 5:
                        atribuirSelo();
                        break;
                    case 6:
                        removerSelo();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } while (opcao != 3);
    }

    private static void exibirMenu() {
        System.out.println("\n=== Avaliação de Marcas ===");
        System.out.println("1. Criar conta ou Login");
        System.out.println("2. Ver marcas");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuAposLogin() {
        System.out.println("\n=== Menu Principal ===");
        System.out.println("1. Cadastrar marca");
        System.out.println("2. Avaliar marca");
        System.out.println("3. Logout");
        System.out.println("4. Buscar marcas");
        System.out.println("5. Atribuir selo");
        System.out.println("6. Remover selo");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarContaOuLogin() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Criar conta");
        System.out.println("2. Fazer login");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (opcao == 1) {
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();
            System.out.print("Digite seu e-mail: ");
            String email = scanner.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();
            gerenciadorUsuarios.registrarUsuario(nome, email, senha);
        } else if (opcao == 2) {
            System.out.print("Digite seu e-mail: ");
            String email = scanner.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();
            gerenciadorUsuarios.login(email, senha);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void listarMarcas() {
        if (marcas.isEmpty()) {
            System.out.println("Não há marcas cadastradas.");
        } else {
            for (Marca marca : marcas) {
                marca.mostrarDetalhes();
            }
        }
    }

    private static void cadastrarMarca() {
        System.out.print("Digite o nome da marca: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a categoria da marca: ");
        String categoria = scanner.nextLine();

        Marca marca = new Marca(nome, categoria);
        marcas.add(marca);
        System.out.println("Marca cadastrada com sucesso!");
    }

    private static void avaliarMarca() {
        System.out.print("Digite o nome da marca para avaliação: ");
        String nomeMarca = scanner.nextLine();
        Marca marcaEncontrada = buscarMarcaPorNome(nomeMarca);

        if (marcaEncontrada != null) {
            System.out.print("Digite seu comentário: ");
            String comentario = scanner.nextLine();
            System.out.print("Digite sua nota (1 a 5): ");
            int nota = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            marcaEncontrada.adicionarAvaliacao(gerenciadorUsuarios.getUsuarioLogado().getNome(), comentario, nota);
            System.out.println("Avaliação registrada com sucesso!");
        } else {
            System.out.println("Marca não encontrada.");
        }
    }

    private static void atribuirSelo() {
        System.out.print("Digite o nome da marca para atribuir selo: ");
        String nomeMarca = scanner.nextLine();
        Marca marca = buscarMarcaPorNome(nomeMarca);

        if (marca != null) {
            System.out.println("Escolha o selo:");
            System.out.println("1. Sustentável");
            System.out.println("2. Qualidade");
            System.out.println("3. Atendimento");
            System.out.println("4. Estilo de Vida");
            int opcaoSelo = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoSelo) {
                case 1:
                    marca.adicionarSelo("Sustentável");
                    break;
                case 2:
                    marca.adicionarSelo("Qualidade");
                    break;
                case 3:
                    marca.adicionarSelo("Atendimento");
                    break;
                case 4:
                    marca.adicionarSelo("Estilo de Vida");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Marca não encontrada.");
        }
    }

    private static void removerSelo() {
        System.out.print("Digite o nome da marca para remover selo: ");
        String nomeMarca = scanner.nextLine();
        Marca marca = buscarMarcaPorNome(nomeMarca);

        if (marca != null) {
            System.out.println("Escolha o selo para remover:");
            System.out.println("1. Sustentável");
            System.out.println("2. Qualidade");
            System.out.println("3. Atendimento");
            System.out.println("4. Estilo de Vida");
            int opcaoSelo = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcaoSelo) {
                case 1:
                    marca.removerSelo("Sustentável");
                    break;
                case 2:
                    marca.removerSelo("Qualidade");
                    break;
                case 3:
                    marca.removerSelo("Atendimento");
                    break;
                case 4:
                    marca.removerSelo("Estilo de Vida");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Marca não encontrada.");
        }
    }

    private static void buscarMarcas() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Em alta (melhores avaliações)");
        System.out.println("2. Mais recentes");
        System.out.println("3. Buscar por nome");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        switch (opcao) {
            case 1:
                marcas.stream()
                        .sorted((m1, m2) -> Double.compare(m2.calcularIndiceConfianca(), m1.calcularIndiceConfianca()))
                        .forEach(Marca::mostrarDetalhes);
                break;
            case 2:
                marcas.stream()
                        .sorted(Comparator.comparing(Marca::getDataCadastro).reversed())
                        .forEach(Marca::mostrarDetalhes);
                break;
            case 3:
                System.out.print("Digite o nome da marca: ");
                String nomeBusca = scanner.nextLine();
                Marca marcaEncontrada = buscarMarcaPorNome(nomeBusca);
                if (marcaEncontrada != null) {
                    marcaEncontrada.mostrarDetalhes();
                } else {
                    System.out.println("Marca não encontrada.");
                }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static Marca buscarMarcaPorNome(String nome) {
        for (Marca marca : marcas) {
            if (marca.getNome().equalsIgnoreCase(nome)) {
                return marca;
            }
        }
        return null;
    }
}