import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "../assets/styles.css";

const RegisterPilot = () => {
  const [numClients, setNumClients] = useState(1);
  const [clients, setClients] = useState([{ name: '', birthday: '' }]);
  const navigate = useNavigate();

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

  const buscarCliente = async (nombre) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_BASE}/clientes/buscar/${nombre}`);

      if (response.status === 404) {
        console.warn(`Cliente no encontrado: ${nombre}, se procederá a registrarlo.`);
        return null; // ✅ No devolvemos `undefined`, sino `null`
      } else if (!response.ok) {
        console.error(`Error buscando cliente ${nombre}: ${response.status}`);
        alert("Error al buscar cliente en el backend.");
        return null;
      }

      return await response.json();
    } catch (error) {
      console.error(`Error en la búsqueda del cliente ${nombre}:`, error);
      return null;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const fechaReserva = new Date().toISOString().split('T')[0];
  
    console.log("Enviando formulario...");
  
    const clientesProcesados = await Promise.all(clients.map(async (client) => {
      const data = await buscarCliente(client.name); // ✅ Llamamos la función de búsqueda
  
      const visitasMensuales = data ? data.visitasMensuales + 1 : 1;
      const cumpleanosHoy = new Date(client.birthday).getMonth() === new Date().getMonth() &&
                            new Date(client.birthday).getDate() === new Date().getDate();
  
      const clientePayload = {
        nombre: client.name,
        cumpleanos: cumpleanosHoy,
        visitasMensuales,
      };
  
      // ✅ Si el cliente no existe, registrarlo automáticamente
      if (!data) {
        await fetch(`${import.meta.env.VITE_BASE}/clientes/registrar`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(clientePayload),
        });
      }
  
      return clientePayload;
    }));

    console.log("Clientes procesados:", clientesProcesados);

    const payload = {
      fechaReserva,
      clientes: clientesProcesados.filter(cliente => cliente !== null), // Filtramos clientes válidos
      cantidadPersonas: numClients,
    };

    console.log("Enviando reserva al backend...", payload);

    try {
      const response = await fetch(`${import.meta.env.VITE_BASE}/reservas/crear`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error("Error en la petición de reserva");

      const data = await response.json();
      console.log("Reserva guardada con éxito:", data);

      alert("Reserva guardada con éxito.");
      navigate('/tarifas', { state: { clientes: payload.clientes, cantidadPersonas: payload.cantidadPersonas } });
    } catch (error) {
      console.error("Error al guardar la reserva:", error);
      alert("Hubo un problema al procesar la reserva. Revisa la consola.");
    }
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