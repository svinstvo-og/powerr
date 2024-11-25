document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.querySelector('section');
    signupForm.style.opacity = 0;

    setTimeout(() => {
        signupForm.style.transition = 'opacity 1s ease-in-out';
        signupForm.style.opacity = 1;
    }, 200);

    const signupButton = document.querySelector('button');
    signupButton.addEventListener('click', function () {
        const passwordInput = document.querySelector('input[type="password"]');
        const confirmPasswordInput = document.querySelector('input[type="password"][name="confirm-password"]');

        const isValid = passwordInput === confirmPasswordInput;

        if (!isValid) {
            signupForm.classList.add('shake');

            setTimeout(() => {
                signupForm.classList.remove('shake');
            }, 300);
        }
    });
});
