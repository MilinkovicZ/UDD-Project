import React from "react";
import { Routes, Route } from "react-router-dom";
import ContractPage from "../Contract/ContractPage";
import LawPage from "../Law/LawPage";
import UploadPage from "../Upload/UploadPage";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<ContractPage />} />
            <Route path="/contracts" element={<ContractPage />} />
            <Route path="/laws" element={<LawPage />} />
            <Route path="/upload" element={<UploadPage />} />
        </Routes>
    );
};

export default AppRoutes;
