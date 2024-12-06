// Fetch data from the Spring API endpoint
async function fetchExercises() {
    try {
        const response = await fetch('/api/exercises/get'); // Use the correct endpoint
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const exercises = await response.json();
        displayExercises(exercises);
    } catch (error) {
        console.error('Error fetching exercises:', error);
    }
}

// Display fetched data in the HTML
function displayExercises(exercises) {
    const list = document.getElementById('exercise-list');
    list.innerHTML = ''; // Clear existing content

    exercises.forEach((exercise) => {
        const listItem = document.createElement('li');
        listItem.textContent = `${exercise.name} - ${exercise.reps} reps at ${exercise.weight} kg`;
        list.appendChild(listItem);
    });
}

// Call the fetch function when the page loads
document.addEventListener('DOMContentLoaded', fetchExercises);


// Initialize Chart.js
const ctx = document.getElementById('exerciseChart').getContext('2d');
let exerciseChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [], // Placeholder
        datasets: [{
            label: 'Weight Progression',
            data: [], // Placeholder
            backgroundColor: 'rgba(46, 204, 113, 0.2)',
            borderColor: 'rgba(39, 174, 96, 1)',
            borderWidth: 2,
            fill: true,
            tension: 0.3
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: true,
                position: 'top'
            }
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Session Number'
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Weight (kg)'
                }
            }
        }
    }
});

// Populate dropdown and log
const exerciseSelect = document.getElementById('exerciseSelect');
const exerciseLog = document.getElementById('exerciseLog');

exerciseSelect.addEventListener('change', updateAnalytics);

function updateAnalytics() {
    const selectedExercise = exerciseSelect.value;

    // Update log
    exerciseLog.innerHTML = logData[selectedExercise].map(item => `<li>${item}</li>`).join('');

    // Update chart
    const weights = exerciseData[selectedExercise];
    exerciseChart.data.labels = weights.map((_, index) => `Session ${index + 1}`);
    exerciseChart.data.datasets[0].data = weights;
    exerciseChart.update();
}

// Initialize the page
updateAnalytics();
