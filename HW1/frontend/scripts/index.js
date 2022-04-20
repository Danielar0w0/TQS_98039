// api url
const api_url =
    "http://localhost:8080/api/world";

// Defining async function
async function getWorldData(url) {

    // Storing response
    const response = await fetch(url);
    // Storing data in form of JSON
    const data = await response.json();
    console.log(data);

    if (response) {
        hideLoader();
    }
    return data;
}

// Calling that async function
getWorldData(api_url).then(data => {

        let continent = `${data[0].Continent}`;
        let totalCases = `${data[0].TotalCases}`;
        let newCases = `${data[0].NewCases}`;
        let activeCases = `${data[0].ActiveCases}`;
        let newRecovered = `${data[0].NewRecovered}`;
        let totalRecovered = `${data[0].TotalRecovered}`;
        let totalDeaths = `${data[0].TotalDeaths}`;

        let content = `
        <h5><img class="mx-2" src="/images/arrow-right-square-fill.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Continent</span> ${continent}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Total Cases</span> ${totalCases}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">New Cases</span> ${newCases}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Active Cases</span> ${activeCases}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">New Recovered</span> ${newRecovered}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Total Recovered</span> ${totalRecovered}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Total Deaths</span> ${totalDeaths}</h5>
        
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Case Fatality Rate</span> ${data[0].Case_Fatality_Rate}</h5>
       
        <h5><img class="mx-2" src="/images/arrow-right-square.svg" alt="Bootstrap" width="28" height="28">
        <span style="color: #ffa500; font-weight: bold">Recovery Proporation</span> ${data[0].Recovery_Proporation}</h5>
        `;

        // Setting innerHTML as tab variable
        document.getElementById("world_data").innerHTML = content;

        var ctxB = document.getElementById("barChart").getContext('2d');
        var myBarChart = new Chart(ctxB, {
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
    }
);

// Function to hide the loader
function hideLoader() {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('loading2').style.display = 'none';
}



