import { Typography, Container, Button, Box } from "@mui/material";
import ContractSearch from "./ContractSearch";
import { useState } from "react";

export default function ContractPage() {
    const [filters, setFilters] = useState([
        {
            operator: "",
            field: "",
            value: "",
            isPhrase: false,
            isNot: false,
            id: 0,
        },
    ]);

    const onAndClick = () => {
        addFilter("AND");
    };

    const onOrClick = () => {
        addFilter("OR");
    };

    const addFilter = (operator) => {
        setFilters((prevFilters) => [
            ...prevFilters,
            {
                operator: operator,
                field: "",
                value: "",
                isPhrase: false,
                isNot: false,
                id: prevFilters.length,
            },
        ]);
    };

    return (
        <Container
            maxWidth="100%"
            style={{ marginTop: "20px", marginLeft: "20px", paddingLeft: "0" }}
        >
            <Typography
                variant="h3"
                align="left"
                gutterBottom
                style={{ color: "#00008b", marginLeft: "20px" }}
            >
                Search Contracts
            </Typography>
            {filters.map((filter, index) => (
                <Box key={filter.id} marginBottom="10px">
                    {index > 0 && (
                        <div
                            style={{
                                color: "#ffffff",
                                fontSize: "1.5em",
                                marginLeft: "23px",
                            }}
                        >
                            {filter.operator}
                        </div>
                    )}
                    <ContractSearch
                        index={index}
                        onAdd={onAndClick}
                        onOr={onOrClick}
                    />
                </Box>
            ))}
            <Button
                variant="contained"
                style={{
                    backgroundColor: "#00008b",
                    color: "#f0f0f0",
                    borderRadius: "50px",
                    padding: "15px 30px",
                    marginTop: "20px",
                    marginLeft: "500px",
                }}
            >
                Search
            </Button>
        </Container>
    );
}
