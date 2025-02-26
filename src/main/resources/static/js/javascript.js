function openModalCriarConta() {
    document.getElementById("criarContaModal").style.display = "block";
}

function closeModalCriarConta() {
    document.getElementById("criarContaModal").style.display = "none";
}

function openModalRecuperarConta() {
    document.getElementById("recuperarContaModal").style.display = "block";
}

function closeModalRecuperarConta() {
    document.getElementById("recuperarContaModal").style.display = "none";
}

function openModalEditarConta() {
    document.getElementById("editarContaModal").style.display = "block";
}

function closeModalEditarConta() {
    document.getElementById("editarContaModal").style.display = "none";
}

function openModalSenhaConta() {
    closeModalEditarConta();

    document.getElementById("nome-senha").value = document.getElementById("nome-editar").value;
    document.getElementById("sobrenome-senha").value = document.getElementById("sobrenome-editar").value;
    document.getElementById("telefone-senha").value = document.getElementById("telefone-editar").value;
    document.getElementById("email-senha").value = document.getElementById("email-editar").value;

    document.getElementById("senhaContaModal").style.display = "block";
}

function closeModalSenhaConta() {
    document.getElementById("senhaContaModal").style.display = "none";
}

window.onclick = function (event) {
    let criarContaModal = document.getElementById("criarContaModal");
    let recuperarContaModal = document.getElementById("recuperarContaModal");
    let editarContaModal = document.getElementById("editarContaModal");
    let senhaContaModal = document.getElementById("senhaContaModal");

    if (event.target === criarContaModal) {
        closeModalCriarConta();
    }

    if (event.target === recuperarContaModal) {
        closeModalRecuperarConta();
    }

    if (event.target === editarContaModal) {
        closeModalEditarConta();
    }

    if (event.target === senhaContaModal) {
        closeModalSenhaConta();
    }
};

function formatarTelefone(input) {
    var telefone = input.value.replace(/\D/g, '');
    telefone = telefone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    input.value = telefone;
}

document.getElementById("telefone-cadastro").addEventListener("input", function () {
    var telefone = this.value.replace(/\D/g, '');

    if (telefone.length !== 11) {
        this.setCustomValidity("Número inválido.");
    } else {
        this.setCustomValidity("");
    }
});

function formatarContato(input) {
    var contato = input.value.replace(/\D/g, '');
    contato = contato.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    input.value = contato;
}

document.getElementById("contato-academia").addEventListener("input", function () {
    var contato = this.value.replace(/\D/g, '');

    if (contato.length !== 11) {
        this.setCustomValidity("Número inválido.");
    } else {
        this.setCustomValidity("");
    }
});

function openModalImagemEvento(imgSrc) {
    const modal = document.getElementById("imagemEventoModal");
    const modalImage = document.getElementById("imagemEventoModalImage");

    modalImage.src = imgSrc;

    modal.style.display = "block";
}

function closeModalImagemEvento() {
    const modal = document.getElementById("imagemEventoModal");
    modal.style.display = "none";
}

window.onclick = function (event) {
    let modal = document.getElementById("imagemEventoModal");
    if (event.target === modal) {
        closeModalImagemEvento();
    }
};