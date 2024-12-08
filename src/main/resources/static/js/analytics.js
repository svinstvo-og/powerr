// Fetch data from the Spring API endpoint
async function fetchExercises() {
    try {
        const response = await fetch('/api/exercise/get'); // Update to match your API endpoint
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const exercises = await response.json();
        initializeAnalytics(exercises);
    } catch (error) {
        console.error('Error fetching exercises:', error);
    }
}

// Initialize dropdown, log, and chart with fetched data
function initializeAnalytics(exercises) {
    const exerciseSelect = document.getElementById('exerciseSelect');
    const exerciseLog = document.getElementById('exerciseLog');
    const ctx = document.getElementById('exerciseChart').getContext('2d');

    // Organize data by exercise name
    const exerciseData = exercises.reduce((acc, exercise) => {
        if (!acc[exercise.name]) {
            acc[exercise.name] = [];
        }
        acc[exercise.name].push({
            reps: exercise.reps,
            weight: exercise.weight,
            created: exercise.created,
        });
        return acc;
    }, {});

    // Populate dropdown
    Object.keys(exerciseData).forEach((exerciseName) => {
        const option = document.createElement('option');
        option.value = exerciseName;
        option.textContent = exerciseName;
        exerciseSelect.appendChild(option);
    });

    // Initialize chart
    const exerciseChart = new Chart(ctx, {
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
                tension: 0.3,
            }],
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top',
                },
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Date',
                    },
                },
                y: {
                    title: {
                        display: true,
                        text: 'Weight (kg)',
                    },
                },
            },
        },
    });

    // Update log and chart on exercise selection
    exerciseSelect.addEventListener('change', () => {
        const selectedExercise = exerciseSelect.value;
        const data = exerciseData[selectedExercise] || [];

        // Update log
        exerciseLog.innerHTML = data
            .map(
                (entry) =>
                    `<li>${entry.created}: ${entry.reps} reps at ${entry.weight} kg</li>`
            )
            .join('');

        // Update chart
        exerciseChart.data.labels = data.map((entry) => `Date ${entry.created}`);
        exerciseChart.data.datasets[0].data = data.map((entry) => entry.weight);
        exerciseChart.update();
    });

    // Trigger initial load
    if (exerciseSelect.options.length > 0) {
        exerciseSelect.value = exerciseSelect.options[0].value;
        exerciseSelect.dispatchEvent(new Event('change'));
    }
}

// Load data on page load
document.addEventListener('DOMContentLoaded', fetchExercises);
