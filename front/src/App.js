import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import CoursPage from "./pages/CoursPage";
import MatierePage from "./pages/MatierePage";
import Navbar from "./components/Navbar";

function App() {
  return (
    <Router>
      <Navbar />
      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/cours" element={<CoursPage />} />
          <Route path="/matieres" element={<MatierePage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
