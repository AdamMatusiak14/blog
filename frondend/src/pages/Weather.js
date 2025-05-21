import React, { useEffect, useState } from "react";

function Weather() {
  const [city, setCity] = useState("Warsaw");
  const [weather, setWeather] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  

  const API_KEY = "6597453ea18555d2fd05ee8ea40aaa3c";

  const fetchWeather = (cityName) => {
    setLoading(true);
    setError(null);
    fetch(
      `https://api.openweathermap.org/data/2.5/weather?q=${cityName}&appid=${API_KEY}&units=metric&lang=pl`
    )
      .then((res) => {
        if (!res.ok) throw new Error("Błąd pobierania danych");
        return res.json();
      })
      .then((data) => {
        setWeather(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchWeather(city);
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchWeather(city);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <label>
          Miasto:{" "}
          <input
            type="text"
            value={city}
            onChange={(e) => setCity(e.target.value)}
          />
        </label>
        <button type="submit">Pokaż pogodę</button>
      </form>
      {loading && <div>Ładowanie pogody...</div>}
      {error && <div>Błąd: {error}</div>}
      {weather && !loading && !error && (
        <div>
          <h2>Pogoda w {weather.name}</h2>
          <p>Temperatura: {weather.main.temp}°C</p>
          <p>Opis: {weather.weather[0].description}</p>
          <p>Wilgotność: {weather.main.humidity}%</p>
          <p>Wiatr: {weather.wind.speed} m/s</p>
        </div>
      )}
    </div>
  );
}

export default Weather;
