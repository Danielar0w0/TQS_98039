// api url
let api_url = "http://localhost:8080/api/recent/";

// Defining async function for country data
async function getCountryData(input) {

    let url = api_url + input;
    // Storing response
    const response = await fetch(url);
    // Storing data in form of JSON
    const data = await response.json();

    if (response) {
        hideLoader();
    }
    return data;
}

// Calling the async functions
getCountryData("canada").then(data => {
    renderData(data);
});


// Function to hide the loader
function hideLoader() {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('loading2').style.display = 'none';
}

// Render data and charts
let myBarChart;
function renderData(data, day) {

    let date = new Date(`${data[0].date}`).toLocaleDateString();
    let country = `${data[0].Country}`;
    let continent = `${data[0].Continent}`;
    let totalCases = `${data[0].total_cases}`;
    let newCases = `${data[0].new_cases}`;
    let totalDeaths = `${data[0].total_deaths}`;
    let newDeaths = `${data[0].new_deaths}`;
    let totalTests = `${data[0].total_tests}`;
    let newTests = `${data[0].new_tests}`;

    if (day) {
        day = new Date(day).getTime();
        for (let d in data) {
            if (new Date(`${data[d].date}`).getTime() === day) {
                date = new Date(`${data[d].date}`).toLocaleDateString();
                country = `${data[d].Country}`;
                continent = `${data[d].Continent}`;
                totalCases = `${data[d].total_cases}`;
                newCases = `${data[d].new_cases}`;
                totalDeaths = `${data[d].total_deaths}`;
                newDeaths = `${data[d].new_deaths}`;
                totalTests = `${data[d].total_tests}`;
                newTests = `${data[d].new_tests}`;

                break;
            }
        }
    }

    console.log(data);
    if (`${country}` !== 'Total:')
        document.getElementById("country").innerText = `${country}`;

    document.getElementById("date").innerText = `${date}`;
    document.getElementById("continent").innerText = `${continent}`;
    document.getElementById("total_cases").innerText = `${totalCases}`;
    document.getElementById("new_cases").innerText = `${newCases}`;
    document.getElementById("total_deaths").innerText = `${totalDeaths}`;
    document.getElementById("new_deaths").innerText = `${newDeaths}`;
    document.getElementById("total_tests").innerText = `${totalTests}`;
    document.getElementById("new_tests").innerText = `${newTests}`;

    const ctxB = document.getElementById("barChart").getContext('2d');

    if (myBarChart !== undefined)
        myBarChart.destroy();

    myBarChart = new Chart(ctxB, {
        type: 'bar',
        data: {
            labels: ["New Cases", "New Deaths", "New Tests"],
            datasets: [{
                label: '# of People',
                data: [newCases, newDeaths, newTests],
                backgroundColor: [
                    'rgba(255,128,0,0.55)',
                    'rgba(255,165,0,0.55)',
                ],
                borderColor: [
                    'rgba(255,128,0,0.55)',
                    'rgba(255,165,0,0.55)',
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    myBarChart.update();
}
