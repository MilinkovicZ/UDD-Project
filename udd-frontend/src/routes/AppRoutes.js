import React from "react";
import { Routes, Route } from "react-router-dom";
import ContractPage from "../components/Contract/ContractPage";
import ContractLocation from "../components/Contract/ContractLocation"
import LawPage from "../components/Law/LawPage";
import UploadPage from "../components/Upload/UploadPage";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<ContractPage />} />
            <Route path="/contracts" element={<ContractPage />} />
            <Route path="/laws" element={<LawPage />} />
            <Route path="/upload" element={<UploadPage />} />
            <Route path="/contracts/location" element={<ContractLocation/>} />
        </Routes>
    );
};

export default AppRoutes;
