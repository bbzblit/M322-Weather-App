window.addEventListener("DOMContentLoaded", () => {
  getParameterValues();
});

// -------------------------------------------------------------------

function getParameterValues() {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  const city = urlParams.get("city");
  const latitude = urlParams.get("lat");
  const longitude = urlParams.get("lon");

  callOpenweathermap(city, latitude, longitude);
  createLinkToForecast(city, latitude, longitude);
  createLinkToMaps(city);
}

// -------------------------------------------------------------------

function callOpenweathermap(city, latitude, longitude) {
  fetch(
    `http://localhost:8080/api/v1/weather/current-weather?latitude=${latitude}&longitude=${longitude}`
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.error("Error calling API");
      }
    })
    .then((data) => {
      // get all the necessary elements from the DOM
      const location = document.querySelector("#data-location");
      const temperature = document.querySelector("#data-temperature");
      const sky = document.querySelector("#data-sky");
      const feelsLike = document.querySelector("#data-feels-like");
      const humidity = document.querySelector("#data-humidity");
      const windSpeed = document.querySelector("#data-wind-speed");

      // set the values of the elements
      location.innerHTML = city;
      temperature.innerHTML = Math.round(data.temperature) + "&#176;C";
      sky.innerHTML = data.description;
      feelsLike.innerHTML = Math.round(data.feels_like) + "&#176;C";
      humidity.innerHTML = data.humidity + "%";
      windSpeed.innerHTML = Math.round(data.wind_speed) + " km/h";
    });
}

// -------------------------------------------------------------------

function createLinkToMaps(city) {
  const mapsLink = document.querySelector("#data-location-link");
  let href = document.createAttribute("href");
  href.value = `https://www.google.com/maps/place/${city}/`;
  mapsLink.setAttributeNode(href);
}

// -------------------------------------------------------------------

function createLinkToForecast(city, latitude, longitude) {
  const forecastLink = document.querySelector("#btn-forecast");
  let url = `/forecast?city=${city}&lat=${latitude}&lon=${longitude}`;
  let href = document.createAttribute("href");

  href.value = url;
  forecastLink.setAttributeNode(href);
}
