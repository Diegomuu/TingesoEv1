import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const Tarifas = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { clientes, cantidadPersonas } = location.state || { clientes: [], cantidadPersonas: 0 };

  const [tarifas, setTarifas] = useState([]);
  const [vueltas, setVueltas] = useState(10); // Número de vueltas predeterminado
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (clientes.length > 0) {
      calcularTarifas();
    }
  }, [clientes, vueltas]);

  const calcularTarifas = () => {
    if (!clientes || clientes.length === 0) {
      setError("No hay clientes registrados para calcular la tarifa.");
      return;
    }

    setLoading(true);
    setError(null);

    const requestBody = {
      clientes,
      vueltas,
    };

    fetch(`${import.meta.env.VITE_BASE}/reservas/calcularTarifa`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(requestBody),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al calcular las tarifas');
        }
        return response.json();
      })
      .then((data) => {
        setTarifas(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Error:', err);
        setError('Hubo un problema al calcular las tarifas. Inténtalo de nuevo.');
        setLoading(false);
      });
  };

  const irAPagar = () => {
    const montoTotal = tarifas.reduce((total, tarifa) => total + tarifa.tarifaFinal, 0);
    navigate('/pagos', { state: { tarifas, montoTotal } });
  };

  return (
    <div>
      <h1>Tarifas</h1>
      <p>Cantidad de personas: {cantidadPersonas}</p>

      <div>
        <label htmlFor="vueltas">Número de vueltas:</label>
        <select
          id="vueltas"
          value={vueltas}
          onChange={(e) => setVueltas(parseInt(e.target.value, 10))}
        >
          <option value={10}>10 vueltas</option>
          <option value={15}>15 vueltas</option>
          <option value={20}>20 vueltas</option>
        </select>
        <button onClick={calcularTarifas}>Calcular Tarifas</button>
      </div>

      {loading && <p>Cargando tarifas...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      <ul>
        {tarifas.map((tarifa, index) => (
          <li key={index}>
            <strong>Nombre:</strong> {tarifa.nombre} <br />
            <strong>Tarifa:</strong> ${tarifa.tarifaFinal} <br />
            <strong>¿Cumpleaños hoy?:</strong> {tarifa.cumpleanos ? 'Sí 🎂' : 'No'} <br />
          </li>
        ))}
      </ul>

      {tarifas.length > 0 && (
        <button onClick={irAPagar} className="btn-primary">
          Ir a Pagar
        </button>
      )}
    </div>
  );
};

export default Tarifas;