// Get list of countries
async function getCountries() {
    // Storing response
    const response = await fetch("http://localhost:8080/api/list");
    // Storing data in form of JSON
    const data = await response.json();

    return data;
}

// Update list of countries
let countries = [];
getCountries().then(data => {
    for (let d in data) {
        let country = `${data[d].Country}`;
        if (country.length > 3)
            countries.push(country);
    }
});

// Enter behaviour on search input
function searchKeypress(e) {
    if (e.key == "Enter") {
        search();
    } else {
        showResults();
    }
}

// Function to update country data
function search() {

    let input = document.getElementById("search_input").value.toLowerCase();

    let day;
    if (document.getElementById("search_day") !== null)
        day = document.getElementById("search_day").value;

    getCountryData(input).then(data => {
        if (data.length > 0) {
            document.getElementById("warning").style.display = "none";
            renderData(data, day);
        } else
            document.getElementById("warning").style.display = "block";
    });
}

// Show autocomplete results
function showResults() {

    let results = [];

    let userInput = document.getElementById("search_input").value.toLowerCase();
    let resultsHTML = document.getElementById("result");

    resultsHTML.innerHTML = "";
    if (userInput.length > 0) {
        results = getResults(userInput);
        resultsHTML.style.display = "block";
        for (let i = 0; i < results.length; i++) {
            resultsHTML.innerHTML += "<li>" + results[i] + "</li>";
        }
    }
};

function getResults(input) {
    const results = [];
    for (let i = 0; i < countries.length; i++) {
        if (input === countries[i].slice(0, input.length).toLowerCase()) {
            results.push(countries[i]);
        }
    }
    return results;
}

// Click behaviour on search button
function clickResult(event) {
    const setValue = event.target.innerText;
    document.getElementById("search_input").value = setValue;
    document.getElementById("result").innerHTML = "";
};