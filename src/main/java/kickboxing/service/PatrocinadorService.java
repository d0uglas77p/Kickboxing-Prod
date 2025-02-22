package kickboxing.service;

import kickboxing.model.Patrocinador;
import kickboxing.repository.PatrocinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class PatrocinadorService {

    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/patrocinadores";

    public void salvarPatrocinador(Patrocinador patrocinador, MultipartFile imagemPatrocinador) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemPatrocinador.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemPatrocinador.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemPatrocinador.transferTo(destino);

            patrocinador.setImagemPatrocinador("/upload/patrocinadores/" + nomeArquivo);
        }

        patrocinadorRepository.save(patrocinador);
    }

    //* AMBIENTE DE PRODUÇÃO ACESSE O ARQUIVO ---- "PRODUCAO.MD" ---- *//
    //* IMPORTANTE PARA ENTENDER COMO VAI FUNCIONAR!!!!!!! *//
}