import axios from "axios";

const API_URL = "http://localhost:8080/matieres"; 

export const getMatieres = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error("Erreur lors de la récupération des matières :", error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || "Erreur inconnue");
  }
};

export const createMatiere = async (matiere) => {
  try {
    const response = await axios.post(API_URL, matiere);
    return response.data;
  } catch (error) {
    console.error("Erreur lors de l'ajout de la matière :", error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || "Erreur inconnue lors de l'ajout de la matière");
  }
};

export const updateMatiere = async (id, matiere) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, matiere);
    return response.data;
  } catch (error) {
    console.error("Erreur lors de la modification de la matière :", error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || "Erreur inconnue lors de la modification de la matière");
  }
};

export const deleteMatiere = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`);
    return true;
  } catch (error) {
    console.error("Erreur lors de la suppression de la matière :", error.response?.data?.message || error.message);
    throw new Error(error.response?.data?.message || "Erreur inconnue lors de la suppression de la matière");
  }
};
