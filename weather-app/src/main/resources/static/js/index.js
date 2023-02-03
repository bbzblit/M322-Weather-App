window.addEventListener("DOMContentLoaded", () => {
  const searchInput = document.querySelector(".search");

  searchInput.addEventListener("keyup", () => {
    let searchInputValue = searchInput.value;
    document.getElementById("matches").innerHTML = "";
    searchInput.style.borderRadius = "40px";

    if (searchInputValue.length >= 3) {
      callGeocoder(searchInputValue);
    }
  });
});

// -------------------------------------------------------------------

function callGeocoder(searchInputValue) {
  const searchInput = document.querySelector(".search");

  fetch(
    `http://localhost:8080/api/v1/geo/search/location?query=${searchInputValue}`
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.error("Error calling API:" + response.body);
      }
    })
    .then((data) => {
      if (data.length != 0) {
        // data.length === 3
        searchInput.style.borderRadius = "20px 20px 0 0";

        let filteredData = filterResponse(data);

        filteredData.forEach((locationItem) => {
          createMatches(
            locationItem.name,
            locationItem.state,
            locationItem.country,
            locationItem.lat,
            locationItem.lon
          );
        });
      } else {
        searchInput.style.borderRadius = "40px";
      }
    });
}

// -------------------------------------------------------------------

function filterResponse(data) {
  const filteredData = data.filter((location) => {
    return (
      location.state !== null &&
      location.state !== undefined &&
      location.country !== "NO"
    );
  });

  return filteredData;
}

// -------------------------------------------------------------------

function createMatches(matchText, state, country, latitude, longitude) {
  let url = `/weather?city=${matchText}&lat=${latitude}&lon=${longitude}`;
  let tag = document.createElement("a");
  let text = document.createTextNode(`${matchText}, ${state} ${country}`);
  let href = document.createAttribute("href");

  href.value = url;
  tag.setAttributeNode(href);
  tag.appendChild(text);

  let element = document.getElementById("matches");

  element.appendChild(tag);
}
