import axios from "axios";

const API_URL = "http://localhost:8080/cours"; // Assure-toi que ton backend tourne sur ce port

export const getCours = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error("Erreur lors de la récupération des cours :", error);
    return [];
  }
};

export const createCours = async (cours) => {
    try {
      const response = await axios.post(API_URL, cours);
      return response.data; // Retourne la réponse en cas de succès
    } catch (error) {
      console.error("Erreur lors de l'ajout du cours :", error.response?.data?.message || error.message);
      throw new Error(error.response?.data?.message || "Erreur inconnue lors de l'ajout du cours");
    }
  };

export const updateCours = async (id, cours) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, cours);
    return response.data;
  } catch (error) {
    console.error("Erreur lors de la modification du cours :", error);
    return null;
  }
};

export const deleteCours = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`);
    return true;
  } catch (error) {
    console.error("Erreur lors de la suppression du cours :", error);
    return false;
  }
};
