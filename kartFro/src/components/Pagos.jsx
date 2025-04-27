import React from "react";
import { useLocation } from "react-router-dom";
import "../assets/styles.css";

const Pagos = () => {
  const location = useLocation();
  const { tarifas, montoTotal } = location.state || { tarifas: [], montoTotal: 0 };

  const enviarComprobante = () => {
    const fechaHoraActual = new Date().toISOString(); // Fecha y hora en formato ISO
    const codigoReserva = "RES" + Date.now(); // Genera código único basado en timestamp
    const nombreReservante = "Diego"; // Puedes reemplazar esto con un input en el futuro
    const vueltasReservadas = tarifas.length > 0 ? tarifas[0].vueltas : 10;

    // Crear detalles de pago por cada cliente
    const detallesPago = tarifas.map((tarifa) => ({
      nombreCliente: tarifa.nombre,
      tarifaBase: tarifa.tarifaBase,
      descuentoGrupo: tarifa.descuentoGrupo || 0,
      descuentoEspecial: tarifa.descuentoEspecial || 0,
      montoFinal: tarifa.tarifaFinal,
      iva: tarifa.tarifaFinal * 0.19, // Aplicamos 19% de IVA
    }));

    // Crear objeto comprobante con toda la información
    const comprobanteData = {
      codigoReserva,
      fechaHoraReserva: fechaHoraActual,
      vueltasReservadas,
      cantidadPersonas: tarifas.length,
      nombreReservante,
      montoTotal,
      ivaTotal: montoTotal * 0.19, // IVA total
      detallesPago,
    };

    fetch(`${import.meta.env.VITE_BASE}/comprobantes/guardar`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(comprobanteData),
    })
      .then((response) => response.text())
      .then((data) => {
        alert("Comprobante guardado con éxito");
        console.log("Respuesta del backend:", data);
      })
      .catch((error) => {
        console.error("Error al guardar el comprobante:", error);
        alert("Hubo un problema al guardar el comprobante. Inténtalo de nuevo.");
      });
  };

  return (
    <div>
      <h1>Pagos</h1>
      <p><strong>Código de reserva:</strong> RES{Date.now()}</p> {/* Se generará al confirmar */}
      <ul>
        {tarifas.map((tarifa, index) => (
          <li key={index}>
            <strong>Nombre:</strong> {tarifa.nombre} <br />
            <strong>Tarifa Base:</strong> ${tarifa.tarifaBase} <br />
            <strong>Descuento Grupo:</strong> ${tarifa.descuentoGrupo || 0} <br />
            <strong>Descuento Especial:</strong> ${tarifa.descuentoEspecial || 0} <br />
            <strong>Monto Final:</strong> ${tarifa.tarifaFinal} <br />
            <strong>IVA:</strong> ${tarifa.tarifaFinal * 0.19}
          </li>
        ))}
      </ul>
      <h2>Monto Total: ${montoTotal}</h2>
      <h2>IVA Total: ${montoTotal * 0.19}</h2>

      <button onClick={enviarComprobante} className="btn-primary">
        Generar Comprobante
      </button>
    </div>
  );
};

export default Pagos;