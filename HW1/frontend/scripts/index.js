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

    let content =
        `<p>Continent: ${data[0].Continent}</p>
        <p>Country: ${data[0].Country}</p>
        <p>Active cases: ${data[0].ActiveCases}</p>
        <p>New cases: ${data[0].NewCases}</p>`;

    // Setting innerHTML as tab variable
    document.getElementById("world_data").innerHTML = content;
    }
);

// Function to hide the loader
function hideLoader() {
    document.getElementById('loading').style.display = 'none';
}



