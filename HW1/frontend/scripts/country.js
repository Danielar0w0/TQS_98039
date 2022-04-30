// api url
let api_url =
    "http://localhost:8080/api/countries/";

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
getCountryData("all").then(data => {
    renderData(data);
});

// Function to hide the loader
function hideLoader() {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('loading2').style.display = 'none';
}

// Render data and charts
let myBarChart;
function renderData(data) {

    let country = `${data[0].Country}`;
    let continent = `${data[0].Continent}`;
    let totalCases = `${data[0].TotalCases}`;
    let newCases = `${data[0].NewCases}`;
    let activeCases = `${data[0].ActiveCases}`;
    let newRecovered = `${data[0].NewRecovered}`;
    let totalRecovered = `${data[0].TotalRecovered}`;
    let totalDeaths = `${data[0].TotalDeaths}`;
    let caseFatality = `${data[0].Case_Fatality_Rate}`;

    if (`${country}` !== 'Total:')
        document.getElementById("country").innerText = `${country}`;
    document.getElementById("continent").innerText = `${continent}`;
    document.getElementById("total_cases").innerText = `${totalCases}`;
    document.getElementById("new_cases").innerText = `${newCases}`;
    document.getElementById("active_cases").innerText = `${activeCases}`;
    document.getElementById("new_recovered").innerText = `${newRecovered}`;
    document.getElementById("total_recovered").innerText = `${totalRecovered}`;
    document.getElementById("total_deaths").innerText = `${totalDeaths}`;
    document.getElementById("case_fatality").innerText = `${caseFatality}`;

    const ctxB = document.getElementById("barChart").getContext('2d');

    if (myBarChart !== undefined)
        myBarChart.destroy();

    myBarChart = new Chart(ctxB, {
        type: 'bar',
        data: {
            labels: ["New Cases", "New Recovered"],
            datasets: [{
                label: '# of People',
                data: [newCases, newRecovered],
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

