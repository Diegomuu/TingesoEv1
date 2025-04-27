import React from 'react';
import { useLocation } from 'react-router-dom';

const Pagos = () => {
  const location = useLocation();
  const { tarifas, montoTotal } = location.state || { tarifas: [], montoTotal: 0 };

  const enviarComprobante = () => {
    const fechaActual = new Date().toISOString().split('T')[0];
    const comprobantes = tarifas.map((tarifa) => ({
      cliente: tarifa.nombre,
      monto: tarifa.tarifaFinal,
      fecha: fechaActual,
    }));

    fetch(`${import.meta.env.VITE_BASE}/comprobantes/crear`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(comprobantes),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al guardar el comprobante');
        }
        return response.json();
      })
      .then((data) => {
        alert('Comprobante guardado con éxito');
        console.log('Respuesta del backend:', data);
      })
      .catch((error) => {
        console.error('Error al guardar el comprobante:', error);
        alert('Hubo un problema al guardar el comprobante. Inténtalo de nuevo.');
      });
  };

  return (
    <div>
      <h1>Pagos</h1>
      <ul>
        {tarifas.map((tarifa, index) => (
          <li key={index}>
            <strong>Nombre:</strong> {tarifa.nombre} <br />
            <strong>Monto:</strong> ${tarifa.tarifaFinal}
          </li>
        ))}
      </ul>
      <h2>Monto Total: ${montoTotal}</h2>
      <button onClick={enviarComprobante} className="btn-primary">
        Generar Comprobante
      </button>
    </div>
  );
};

export default Pagos;