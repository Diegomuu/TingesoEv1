import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "../assets/styles.css";

const RegisterPilot = () => {
  const [numClients, setNumClients] = useState(1);
  const [clients, setClients] = useState([{ name: '', birthday: '' }]);
  const navigate = useNavigate(); // Hook para redirigir

  const handleNumClientsChange = (e) => {
    const value = parseInt(e.target.value, 10);
    setNumClients(value);

    const updatedClients = [...clients];
    while (updatedClients.length < value) {
      updatedClients.push({ name: '', birthday: '' });
    }
    while (updatedClients.length > value) {
      updatedClients.pop();
    }
    setClients(updatedClients);
  };

  const handleClientChange = (index, field, value) => {
    const updatedClients = [...clients];
    updatedClients[index][field] = value;
    setClients(updatedClients);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      fechaReserva: new Date().toISOString().split('T')[0],
      clientes: clients.map(client => ({
        nombre: client.name,
        cumpleanos: new Date(client.birthday).getMonth() === new Date().getMonth() &&
                    new Date(client.birthday).getDate() === new Date().getDate(),
        visitasMensuales: Math.floor(Math.random() * 10),
      })),
      cantidadPersonas: numClients,
    };

    console.log('Datos enviados al backend:', payload);

    fetch(`${import.meta.env.VITE_BASE}/reservas/crear`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error en la solicitud');
        }
        return response.json();
      })
      .then(data => {
        console.log('Reserva guardada:', data);
        alert('Reserva guardada con éxito.');

        // Redirigir a la vista Tarifas con los datos
        navigate('/tarifas', { state: { clientes: payload.clientes, cantidadPersonas: payload.cantidadPersonas } });
      })
      .catch(error => {
        console.error('Error al guardar la reserva:', error);
        alert('Hubo un error al enviar los datos. Por favor, inténtalo de nuevo.');
      });
  };

  return (
    <div>
      <h1>Registrar Piloto</h1>
      <p>Selecciona cuántas personas van a utilizar el servicio (máximo 15):</p>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="numClients">Número de personas:</label>
          <select id="numClients" value={numClients} onChange={handleNumClientsChange}>
            {Array.from({ length: 15 }, (_, i) => i + 1).map(num => (
              <option key={num} value={num}>
                {num}
              </option>
            ))}
          </select>
        </div>

        <div>
          {clients.map((client, index) => (
            <div key={index} className="client-form">
              <h3>Persona {index + 1}</h3>
              <label>
                Nombre:
                <input
                  type="text"
                  value={client.name}
                  onChange={(e) => handleClientChange(index, 'name', e.target.value)}
                  required
                />
              </label>
              <label>
                Fecha de cumpleaños:
                <input
                  type="date"
                  value={client.birthday}
                  onChange={(e) => handleClientChange(index, 'birthday', e.target.value)}
                  required
                />
              </label>
            </div>
          ))}
        </div>

        <button type="submit" className="btn-primary">
          Enviar
        </button>
      </form>
    </div>
  );
};

export default RegisterPilot;