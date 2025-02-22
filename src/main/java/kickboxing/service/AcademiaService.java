package kickboxing.service;

import kickboxing.model.Academia;
import kickboxing.repository.AcademiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AcademiaService {

    @Autowired
    private AcademiaRepository academiaRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/academias";

    public void salvarAcademia(Academia academia, MultipartFile imagemAcademia) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemAcademia.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemAcademia.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemAcademia.transferTo(destino);

            academia.setImagemAcademia("/upload/academias/" + nomeArquivo);
        }

        academiaRepository.save(academia);
    }

    //* AMBIENTE DE PRODUÇÃO ACESSE O ARQUIVO ---- "PRODUCAO.MD" ---- *//
    //* IMPORTANTE PARA ENTENDER COMO VAI FUNCIONAR!!!!!!! *//
}