// api url
let api_url = "http://localhost:8080/api/news/";

// Defining async function for country data
async function getNewsData(input) {

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
getNewsData("covid").then(data => {
    renderData(data, "Covid");
});

// Function to hide the loader
function hideLoader() {
    document.getElementById('loading').style.display = 'none';
}

// Render data
function renderData(data, input) {

    console.log(data);
    let newsPanels = [];
    for (let idx in data) {
        let news = data[idx];
        newsPanels.push(
            `<div class="col-lg-3 col-md-5 col-sm-5 mx-1 mb-4 mt-1">
                <div class="card p-3" style="min-height: 100%">
                    <div class="card-body">                  
                        <p class="card-text mb-2" style="font-size: 0.7vw">${input}</p>  
                        <h5 class="card-title mb-4">${news.title} <span style="font-size: 0.8vw; color: #FFA500FF" class="mb-4">${news.reference}</span></h5>
                        <p class="mb-4">${news.content}</p>
                        <a href="${news.link}" style="text-decoration: none; font-weight: bold; color: #FFA500FF;">Read More</a> 
                        <p class="mt-2">${news.pubDate}</p>
                    </div>
                </div>
            </div>
        </div>`
        );
    }

    document.getElementById("news").innerHTML = newsPanels;
}

function categorySelected(event) {
    let input = event.target.value;
    getNewsData(input.toLowerCase()).then(data => {
        renderData(data, input);
    });

}