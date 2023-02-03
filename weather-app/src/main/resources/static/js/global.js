window.addEventListener("DOMContentLoaded", (e) => {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const latitude = urlParams.get("lat");
  const longitude = urlParams.get("lon");

  callTimeZoneDatabase(latitude, longitude);
});

// -------------------------------------------------------------------

function callTimeZoneDatabase(latitude, longitude) {
  fetch(
    `http://api.timezonedb.com/v2.1/get-time-zone?key=7ZM03JHT7YH5&by=position&lat=${latitude}&lng=${longitude}&format=json`
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.error("Error calling API");
      }
    })
    .then((data) => {
      handleTime(data);
    });
}

// -------------------------------------------------------------------

function handleTime(data) {
  const localTime = data.formatted.substr(11, 18).replaceAll(":", "");
  const localTimeFormatted = parseInt(localTime);
  const localHour = parseInt(retrieveLocalHour(localTimeFormatted));

  setBackground(localHour);
}

// -------------------------------------------------------------------

function retrieveLocalHour(localTimeFormatted) {
  const time = localTimeFormatted;

  if (time.toString().length === 5) {
    return time.toString().substr(0, 1);
  } else if (time.toString().length === 6) {
    return time.toString().substr(0, 2);
  }
}

// -------------------------------------------------------------------

function setBackground(localHour) {
  const appContainer = document.querySelector(".app-container");

  // order: sunrise, morning, lunch, afternoon, evening, night, night
  const dayTime = [
    localHour >= 7 && localHour < 9,
    localHour >= 9 && localHour < 11,
    localHour >= 11 && localHour < 15,
    localHour >= 15 && localHour < 18,
    localHour >= 18 && localHour < 21,
    localHour >= 21 && localHour <= 24,
    localHour >= 0 && localHour < 7,
  ];

  const dayTimeStyle = [
    "linear-gradient(0deg, #ffe5c1 0%, #ffb64f 50%, #ff7c1f 100%)",
    "linear-gradient(0deg, #eeedff 0%, #aec1ff 50%, #787aff 100%)",
    "linear-gradient(0deg, #b9efff 0%, #3dbce3 50%, #0060cd 100%)",
    "linear-gradient(0deg, #ffea82 0%, #ffbd39 50%, #ff7722 100%)",
    "linear-gradient(0deg, #c3beff 0%, #5f80ec 50%, #0c3dae 100%)",
    "linear-gradient(0deg, #7c67b2 0%, #442bc5 50%, #250672 100%)",
  ];

  dayTime.forEach((hour, i) => {
    if (hour) {
      appContainer.style.background = dayTimeStyle[i];
    }
  });

  if (dayTime[5] || dayTime[6]) {
    appContainer.style.background = dayTimeStyle[5];

    const location = document.querySelector("#data-location");
    const temp = document.querySelector("#data-temperature");
    const desc = document.querySelector("#data-sky");
    const marker = document.querySelector("#data-marker");
    const title = document.querySelector("#data-forecast-location");

    const toStyle = [location, temp, desc, marker, title];

    toStyle.forEach((tag) => {
      if (document.body.contains(tag)) {
        tag.style.filter = "invert(100%)";
      }
    });
  }
}
