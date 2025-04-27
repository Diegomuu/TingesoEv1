import React, { useEffect, useState } from "react";

const RackSemanal = () => {
  const [rackSemanal, setRackSemanal] = useState([]);

  useEffect(() => {
    fetch(`${import.meta.env.VITE_BASE}/rack/semanal`)
      .then((response) => response.json())
      .then((data) => setRackSemanal(data))
      .catch((error) => console.error("Error al cargar el rack semanal:", error));
  }, []);

  return (
    <div>
      <h1>Rack Semanal</h1>
      <table border="1">
        <thead>
          <tr>
            <th>Fecha</th>
            <th>Código Reserva</th>
            <th>Reservante</th>
            <th>Vueltas</th>
            <th>Personas</th>
            <th>Monto Total</th>
          </tr>
        </thead>
        <tbody>
          {rackSemanal.map((dia) => (
            dia.reservasDelDia.map((reserva, index) => (
              <tr key={index}>
                <td>{dia.fecha}</td>
                <td>{reserva.codigoReserva}</td>
                <td>{reserva.nombreReservante}</td>
                <td>{reserva.vueltasReservadas}</td>
                <td>{reserva.cantidadPersonas}</td>
                <td>${reserva.montoTotal}</td>
              </tr>
            ))
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RackSemanal;