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