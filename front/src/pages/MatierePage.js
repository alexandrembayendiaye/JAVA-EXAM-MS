import { useEffect, useState } from "react";
import { getMatieres, createMatiere, updateMatiere, deleteMatiere } from "../services/matiereService";
import { getCours } from "../services/coursService";

const MatierePage = () => {
  const [matieres, setMatieres] = useState([]);
  const [filteredMatieres, setFilteredMatieres] = useState([]); // Liste filtrée pour la recherche
  const [cours, setCours] = useState([]);
  const [id, setId] = useState(null);
  const [nom, setNom] = useState("");
  const [description, setDescription] = useState("");
  const [coursId, setCoursId] = useState("");
  const [search, setSearch] = useState(""); // Recherche dynamique
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    fetchMatieres();
    fetchCours();
  }, []);

  const fetchMatieres = async () => {
    try {
      const data = await getMatieres();
      setMatieres(data);
      setFilteredMatieres(data); // Par défaut, affiche toutes les matières
      setError("");
    } catch (err) {
      setError(err.message);
    }
  };

  const fetchCours = async () => {
    try {
      const data = await getCours();
      setCours(data);
      setError("");
    } catch (err) {
      setError("Impossible de récupérer les cours !");
    }
  };

  const handleSearch = (e) => {
    const query = e.target.value.toLowerCase();
    setSearch(query);

    if (query === "") {
      setFilteredMatieres(matieres);
    } else {
      const filtered = matieres.filter((m) => m.nom.toLowerCase().includes(query));
      setFilteredMatieres(filtered);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    if (!nom || !description || !coursId) {
      setError("Tous les champs sont obligatoires !");
      return;
    }

    try {
      if (id) {
        const updatedMatiere = { id, nom, description, coursId, archive: false };
        await updateMatiere(id, updatedMatiere);
        setMessage("Matière mise à jour avec succès !");
      } else {
        const newMatiere = { nom, description, coursId, archive: false };
        await createMatiere(newMatiere);
        setMessage("Matière ajoutée avec succès !");
      }
      resetForm();
      fetchMatieres();
    } catch (err) {
      setError(err.message);
      setMessage("");
    }
  };

  const handleEdit = (matiere) => {
    setId(matiere.id);
    setNom(matiere.nom);
    setDescription(matiere.description);
    setCoursId(matiere.coursId);
  };

  const handleDelete = async (id) => {
    try {
      await deleteMatiere(id);
      fetchMatieres();
    } catch (err) {
      setError(err.message);
    }
  };

  const resetForm = () => {
    setId(null);
    setNom("");
    setDescription("");
    setCoursId("");
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center text-primary fw-bold">Gestion des Matières</h1>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      {/* Barre de recherche */}
      <div className="row justify-content-center mb-4">
        <div className="col-md-6">
          <input
            type="text"
            className="form-control"
            placeholder="Rechercher une matière..."
            value={search}
            onChange={handleSearch}
          />
        </div>
      </div>

      {/* Formulaire d'ajout/modification */}
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h2 className="text-center">{id ? "Modifier la Matière" : "Ajouter une Matière"}</h2>
          <form onSubmit={handleSubmit} className="mb-4">
            <div className="mb-3">
              <label className="form-label">Nom</label>
              <input type="text" className="form-control" value={nom} onChange={(e) => setNom(e.target.value)} required />
            </div>
            <div className="mb-3">
              <label className="form-label">Description</label>
              <textarea className="form-control" value={description} onChange={(e) => setDescription(e.target.value)} required></textarea>
            </div>
            <div className="mb-3">
              <label className="form-label">Cours associé</label>
              <select className="form-control" value={coursId} onChange={(e) => setCoursId(e.target.value)} required>
                <option value="">-- Sélectionner un cours --</option>
                {cours.map((c) => (
                  <option key={c.id} value={c.id}>{c.nom}</option>
                ))}
              </select>
            </div>
            <button type="submit" className="btn btn-primary w-100">{id ? "Mettre à jour" : "Ajouter"}</button>
            {id && <button type="button" className="btn btn-secondary w-100 mt-2" onClick={resetForm}>Annuler</button>}
          </form>
        </div>
      </div>

      {/* Liste des matières filtrées */}
      <div className="row justify-content-center">
        <div className="col-md-10">
          <h2 className="text-center mt-4">Liste des Matières</h2>
          <div className="row">
            {filteredMatieres.length === 0 ? (
              <p className="text-muted text-center">Aucune matière trouvée.</p>
            ) : (
              filteredMatieres.map((m) => (
                <div key={m.id} className="col-md-4">
                  <div className="card mb-3 shadow-sm">
                    <div className="card-body text-center">
                      <h5 className="card-title">{m.nom}</h5>
                      <p className="card-text">{m.description}</p>
                      <p className="text-muted">Cours : {m.coursNom}</p>
                      <button className="btn btn-warning me-2" onClick={() => handleEdit(m)}>Modifier</button>
                      <button className="btn btn-danger" onClick={() => handleDelete(m.id)}>Supprimer</button>
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

export default MatierePage;
