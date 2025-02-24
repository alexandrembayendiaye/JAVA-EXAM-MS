import { useEffect, useState } from "react";
import { getCours, createCours, deleteCours } from "../services/coursService";

const CoursPage = () => {
  const [cours, setCours] = useState([]);
  const [nom, setNom] = useState("");
  const [description, setDescription] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    fetchCours();
  }, []);

  const fetchCours = async () => {
    try {
      const data = await getCours();
      setCours(data);
      setError(""); // Efface l'erreur si tout est OK
    } catch (err) {
      setError(err.message); // Affiche l'erreur du backend
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage(""); // On vide les messages à chaque tentative
    setError("");

    if (!nom || !description) {
      setError("Tous les champs sont obligatoires !");
      return;
    }

    try {
      const newCours = { nom, description, archive: false };
      await createCours(newCours);
      setMessage("Cours ajouté avec succès !");
      setNom("");
      setDescription("");
      fetchCours();
    } catch (err) {
      setError(err.message); // Affiche le message exact du backend
      setMessage(""); // On empêche l'affichage du message de succès
    }
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center text-primary fw-bold">Gestion des Cours</h1>

      {/* Affichage des messages */}
      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      {/* Formulaire d'ajout de cours */}
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h2 className="text-center">Ajouter un Cours</h2>
          <form onSubmit={handleSubmit} className="mb-4">
            <div className="mb-3">
              <label className="form-label">Nom du cours</label>
              <input 
                type="text" 
                className="form-control" 
                value={nom} 
                onChange={(e) => setNom(e.target.value)} 
                required 
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Description</label>
              <textarea 
                className="form-control" 
                value={description} 
                onChange={(e) => setDescription(e.target.value)} 
                required
              ></textarea>
            </div>
            <button type="submit" className="btn btn-primary w-100">Ajouter</button>
          </form>
        </div>
      </div>

      {/* Liste des cours */}
      <div className="row justify-content-center">
        <div className="col-md-10">
          <h2 className="text-center mt-4">Liste des Cours</h2>
          <div className="row">
            {cours.length === 0 ? (
              <p className="text-muted text-center">Aucun cours disponible.</p>
            ) : (
              cours.map((c) => (
                <div key={c.id} className="col-md-4">
                  <div className="card mb-3 shadow-sm">
                    <div className="card-body text-center">
                      <h5 className="card-title">{c.nom}</h5>
                      <p className="card-text">{c.description}</p>
                      <button className="btn btn-danger" onClick={() => deleteCours(c.id).then(fetchCours)}>Supprimer</button>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CoursPage;
