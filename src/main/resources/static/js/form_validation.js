//Взято с https://bootstrap-4.ru/docs/5.0/forms/validation/
// function validateForm() {
//     'use strict'
//     // Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
//     const forms = document.querySelectorAll('.needs-validation');
//     //const genre = document.getElementById("genre");
//     // Зацикливайтесь на них и предотвращайте отправку
//     Array.prototype.slice.call(forms)
//         .forEach(function (form) {
//             form.addEventListener('submit', function (event) {
//                 if (!form.checkValidity()) {
//                     event.preventDefault()
//                     event.stopPropagation()
//                 }
//                 // TODO: Проверка на жанр (что жанр выбран)
//                 // if (genre.value === 'default') {
//                 //     alert("Пожалуйста, выберете жанр!");
//                 //     event.preventDefault()
//                 //     event.stopPropagation()
//                 //     return false;
//                 // }
//                 form.classList.add('was-validated')
//             }, false)
//         })
// }
function validateForm() {
    {
        'use strict'

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        const forms = document.querySelectorAll('.needs-validation')

        // Loop over them and prevent submission
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
    }
}