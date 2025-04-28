import "./Home.css";
import Navbar from './Navbar';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div>
      <Navbar />
      <div className="home-container">
        <h1 className="home-title">🏁 Bienvenido a SpeedKart!</h1>
        <p className="home-description">
          La mejor plataforma para gestionar carreras de karting, pilotos, tiempos y resultados. 
          Organiza eventos, revisa estadísticas y disfruta de la velocidad. 🚀
        </p>
        <div className="home-buttons">
          <button className="btn-primary">Ver Reportes</button>
          <Link to="/register-pilot">
            <button className="btn-secondary">Registrar Piloto</button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
