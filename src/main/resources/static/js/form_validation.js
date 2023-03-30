function validateForm() {
    'use strict'
    // Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
    const forms = document.getElementById('login');
    const genre = document.getElementById("genre");
    const login = document.getElementById("login")
    // Зацикливайтесь на них и предотвращайте отправку
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                // TODO: Проверка на жанр (что жанр выбран)
                // if (genre.value === 'default') {
                //     alert("Пожалуйста, выберете жанр!");
                //     event.preventDefault()
                //     event.stopPropagation()
                //     return false;
                // }
                form.classList.add('was-validated')
            }, false)
        })
}
