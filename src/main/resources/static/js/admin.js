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

window.onclick = function (event) {
    let criarContaModal = document.getElementById("criarContaModal");
    let recuperarContaModal = document.getElementById("recuperarContaModal");

    if (event.target === criarContaModal) {
        closeModalCriarConta();
    }

    if (event.target === recuperarContaModal) {
        closeModalRecuperarConta();
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