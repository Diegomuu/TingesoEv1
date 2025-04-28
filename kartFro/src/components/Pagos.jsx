import React from "react";
import { useLocation } from "react-router-dom";
import "../assets/styles.css";
import Navbar from "./Navbar";

const Pagos = () => {
  const location = useLocation();
const { tarifas, montoTotal, vueltas } = location.state || { tarifas: [], montoTotal: 0, vueltas: 10 };

console.log("Vueltas recibidas en Pagos:", vueltas); // ✅ Verificación en la consola
console.log("Tarifas recibidas:", tarifas);
  console.log("Monto total recibido:", montoTotal);

  const enviarComprobante = async () => {
    const fechaHoraActual = new Date().toISOString();
    
    const comprobanteData = {
      fechaHoraReserva: fechaHoraActual,
      vueltasReservadas: vueltas || 10, // ✅ Evita valores indefinidos
      cantidadPersonas: tarifas.length,
      nombreReservante: "Diego",
      montoTotal: montoTotal || 0, // ✅ Evita valores `undefined`
      ivaTotal: (montoTotal || 0) * 0.19,
      detallesPago: tarifas.map((tarifa) => ({
        nombreCliente: tarifa.nombre,
        tarifaBase: tarifa.tarifaBase || 0, // ✅ Evita `undefined`
        descuentoGrupo: tarifa.descuentoGrupo || 0,
        descuentoEspecial: tarifa.descuentoEspecial || 0,
        montoFinal: tarifa.tarifaFinal || 0,
        iva: (tarifa.tarifaFinal || 0) * 0.19,
      })),
    };
  
    console.log("Datos del comprobante a enviar:", comprobanteData); // ✅ Verificación antes de enviar
  
    try {
      const response = await fetch(`${import.meta.env.VITE_BASE}/comprobantes/guardar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(comprobanteData),
      });
  
      const responseText = await response.text(); // ✅ Verifica si la respuesta es texto plano
      console.log("Respuesta del backend:", responseText);
  
      if (!response.ok) throw new Error("Error al guardar el comprobante");
  
      const data = JSON.parse(responseText); // ✅ Convierte la respuesta en JSON correctamente
  
      alert(data.message || "Comprobante guardado con éxito");
      descargarExcel(data.codigoReserva);
  
    } catch (error) {
      console.error("Error al guardar el comprobante:", error);
      alert("Hubo un problema al guardar el comprobante. Revisa la consola.");
    }
  };
  

  const descargarExcel = (codigoReserva) => {
    fetch(`${import.meta.env.VITE_BASE}/comprobantes/excel/${codigoReserva}`)
      .then((response) => response.blob())
      .then((blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "comprobante.xlsx";
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
      })
      .catch((error) => console.error("Error al generar Excel:", error));
  };

  return (
    <div>
      <Navbar /> {/* ✅ Se agrega el Navbar */}
      <h1>Pagos</h1>
      <ul>
        {tarifas.map((tarifa, index) => (
          <li key={index}>
            <strong>Nombre:</strong> {tarifa.nombre} <br />
            <strong>Tarifa Base:</strong> ${tarifa.tarifaBase || 0} <br />
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
      <button onClick={() => descargarExcel("RES123456")} className="btn-primary">
        Descargar Comprobante en Excel
      </button>
    </div>
  );
};

export default Pagos;
