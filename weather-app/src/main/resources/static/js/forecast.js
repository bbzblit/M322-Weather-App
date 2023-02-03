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

  callOpenweathermapForecast(city, latitude, longitude);
  createLinkToWeather(city, latitude, longitude);
}

// -------------------------------------------------------------------

function callOpenweathermapForecast(city, latitude, longitude) {
  fetch(
    `http://localhost:8080/api/v1/weather/forecast?latitude=${latitude}&longitude=${longitude}&clockTime=12:00:00`
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.error("Error calling API");
      }
    })
    .then((data) => {
      handleDom(city, data);
    });
}

// -------------------------------------------------------------------

function handleDom(city, data) {
  setTitle(city);
  appendImages(data);
  appendText(data);
}

// -------------------------------------------------------------------

function appendText(data) {
  const dayOne = document.querySelector("#data-forecast-1");
  const dayTwo = document.querySelector("#data-forecast-2");
  const dayThree = document.querySelector("#data-forecast-3");
  const dayFour = document.querySelector("#data-forecast-4");
  const dayFive = document.querySelector("#data-forecast-5");

  const forecastDayArray = [dayOne, dayTwo, dayThree, dayFour, dayFive];

  appendTemp(data, forecastDayArray);
}

// -------------------------------------------------------------------

function appendImages(data) {
  const dayOne = document.querySelector("#data-forecast-img-1");
  const dayTwo = document.querySelector("#data-forecast-img-2");
  const dayThree = document.querySelector("#data-forecast-img-3");
  const dayFour = document.querySelector("#data-forecast-img-4");
  const dayFive = document.querySelector("#data-forecast-img-5");

  const forecastDayArray = [dayOne, dayTwo, dayThree, dayFour, dayFive];

  forecastDayArray.forEach((day, i) => {
    let description = data[i].description;
    let assetLink = checkDescription(description);
    let src = document.createAttribute("src");

    src.value = assetLink;
    day.setAttributeNode(src);
  });
}

// -------------------------------------------------------------------

function appendTemp(data, forecastDayArray) {
  forecastDayArray.forEach((day, i) => {
    let temperature = data[i].temperature;

    day.innerHTML =
      appendWeekday(data, i) + " " + Math.round(temperature) + "&#176;C";
  });
}

// -------------------------------------------------------------------

function appendWeekday(data, i) {
  let date = new Date(data[i].date);
  let weekday = date.toString().slice(0, 3);

  return weekday;
}

// -------------------------------------------------------------------

function checkDescription(description) {
  const basePath = "/assets";

  switch (description) {
    case "Rain":
      return `${basePath}/rain.png`;
    case "Clouds":
      return `${basePath}/clouds.png`;
    case "Sun":
      return `${basePath}/sun.png`;
    case "Clear":
      return `${basePath}/sun.png`;
    case "Mist":
      return `${basePath}/mist.png`;
    case "Fog":
      return `${basePath}/mist.png`;
    case "Snow":
      return `${basePath}/snow.png`;
    case "Extreme":
      return `${basePath}/extreme.png`;
  }
}

// -------------------------------------------------------------------

function setTitle(city) {
  const title = document.querySelector("#data-forecast-location");

  title.innerHTML = `The next five days in ${city}`;
}

// -------------------------------------------------------------------

function createLinkToWeather(city, latitude, longitude) {
  const weatherLink = document.querySelector("#btn-weather");
  let url = `/weather?city=${city}&lat=${latitude}&lon=${longitude}`;
  let href = document.createAttribute("href");

  href.value = url;
  weatherLink.setAttributeNode(href);
}
