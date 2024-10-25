// Function to toggle modal visibility
function toggleModal(modalId) {
    const modal = document.getElementById(modalId);
    const overlay = document.getElementById('modalOverlay');
    if (modal.style.display === 'block') {
        modal.style.display = 'none';
        overlay.style.display = 'none';
    } else {
        modal.style.display = 'block';
        overlay.style.display = 'block';
    }
}
