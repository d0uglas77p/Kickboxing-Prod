package kickboxing.service;

import kickboxing.model.Evento;
import kickboxing.model.Patrocinador;
import kickboxing.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/eventos";

    public void salvarEvento(Evento evento, MultipartFile imagemEvento) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemEvento.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemEvento.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemEvento.transferTo(destino);

            evento.setImagemEvento("/upload/eventos/" + nomeArquivo);
        }

        eventoRepository.save(evento);
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public void excluirEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        // Remover imagem
        File imagem = new File(System.getProperty("user.dir") + "/src/main/resources/static" + evento.getImagemEvento());
        if (imagem.exists()) {
            imagem.delete();
        }

        eventoRepository.deleteById(id);
    }

    //* AMBIENTE DE PRODUÇÃO ACESSE O ARQUIVO ---- "PRODUCAO.MD" ---- *//
    //* IMPORTANTE PARA ENTENDER COMO VAI FUNCIONAR!!!!!!! *//
}