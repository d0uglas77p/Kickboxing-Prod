Para que o código funcione tanto no ambiente local quanto em produção no Tomcat, 
você pode fazer a seguinte adaptação no seu serviço, usando uma variável de ambiente 
ou uma configuração que permita alterar o diretório de upload dependendo de onde o aplicativo está sendo executado.
Passo 1: Modificar o código para usar a variável de ambiente ou propriedade externa


No seu código atual, você está utilizando um caminho fixo para o ambiente local:
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/eventos";

Isso funciona no desenvolvimento, mas para produção no Tomcat, você precisará ajustar esse caminho para apontar 
para a pasta de upload no servidor.


Passo 2: Ajustar o caminho para produção

Você pode usar uma variável de ambiente para determinar o diretório de upload, como já discutido antes. 
Se não estiver configurada, o código pode cair no caminho local de desenvolvimento. Aqui está uma versão ajustada:

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    // Usar uma variável de ambiente para determinar o diretório de upload
    private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR") != null ? 
            System.getenv("UPLOAD_DIR") : "src/main/resources/static/upload/eventos"; // Caminho padrão para desenvolvimento

    public void salvarEvento(Evento evento, MultipartFile imagemEvento) throws IOException {
        // O diretório de upload será determinado pela variável de ambiente ou pelo valor padrão
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // Cria o diretório se não existir
        }

        if (!imagemEvento.isEmpty()) {
            // Gera um nome único para o arquivo
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemEvento.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemEvento.transferTo(destino);

            // Salva o caminho relativo para o banco de dados (a URL da imagem)
            evento.setImagemEvento("/upload/eventos/" + nomeArquivo);
        }

        eventoRepository.save(evento);
    }
}

Passo 3: Configurar a variável de ambiente no servidor Tomcat

Quando você for subir a aplicação no servidor Tomcat, é necessário configurar a variável de ambiente UPLOAD_DIR para o 
diretório onde as imagens serão salvas. No caso do Tomcat, isso pode ser feito de duas maneiras:
1. Usando o setenv.sh ou setenv.bat (para Linux ou Windows)

   Navegue até o diretório bin do seu Tomcat.
   Crie ou edite o arquivo setenv.sh (Linux/macOS) ou setenv.bat (Windows).

No arquivo, adicione a seguinte linha:
export UPLOAD_DIR=/path/to/Tomcat/apache-tomcat-8.5.73/webapps/Kickboxing/Upload/Eventos

Esse caminho deverá apontar para o diretório onde você deseja armazenar as imagens no servidor.

2. Usando o context.xml (Tomcat)

Alternativamente, você pode definir um contexto no context.xml para definir a variável de ambiente. 
No arquivo META-INF/context.xml ou <TOMCAT_HOME>/conf/context.xml, adicione:

<Context>
    <Environment name="UPLOAD_DIR" value="/path/to/Tomcat/apache-tomcat-8.5.73/webapps/Kickboxing/Upload/Eventos" type="java.lang.String" />
</Context>


Passo 4: Testar localmente e em produção

    Localmente: O código vai utilizar o diretório de upload padrão (src/main/resources/static/upload/eventos).
    Em produção (Tomcat): O código usará o valor configurado na variável de ambiente UPLOAD_DIR, que será o caminho para o diretório dentro de webapps.

Passo 5: Acessar a imagem

Após isso, a imagem será salva no diretório de produção e você poderá acessá-la via URL, algo como:

    http://seu-servidor.com/Kickboxing/Upload/Eventos/nome_da_imagem.jpg

