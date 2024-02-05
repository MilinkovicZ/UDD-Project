import axios from "axios";
const baseURL = process.env.REACT_APP_BASE_URL;

const searchContracts = async (query) => {
  try {
    console.log(query);
    const response = await axios.post(
      `${baseURL}search/contract/advanced`,
      query,
      {
        headers: {
          "Content-Type": "text/plain",
        },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error;
  }
};

const searchContractsByLocation = async (locationData) => {
  try {
    const response = await axios.post(
      `${baseURL}search/contract/location`,
      locationData
    );

    return response.data;
  } catch (error) {
    console.error("Error fetching data:", error);
    throw error;
  }
};

const searchLaws = async (value) => {
  try {
    const response = await axios.post(`${baseURL}search/law`, value);

    return response.data;
  } catch (error) {
    console.error("Error fetching law data:", error);
    throw error;
  }
};

const uploadDocuments = async (uploadData) => {
  try {
    const formData = new FormData();
    formData.append("contract", uploadData.contract);

    uploadData.laws.forEach((lawFile, index) => {
      formData.append(`laws[${index}]`, lawFile);
    });

    await axios.post(`${baseURL}documents/upload`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  } catch (error) {
    console.error("Error uploading documents:", error);
    throw error;
  }
};

const downloadDocument = async (objectName) => {
  try {
    const downloadUrl = `${baseURL}download/${objectName}`;
    window.open(downloadUrl, "_blank");
    return true;
  } catch (error) {
    console.error("Error downloading document:", error);
    throw error;
  }
};

export {
  searchContracts,
  searchContractsByLocation,
  searchLaws,
  uploadDocuments,
  downloadDocument,
};
