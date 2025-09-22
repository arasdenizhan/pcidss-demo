import "react-credit-cards/es/styles-compiled.css";

import { ChangeEvent, useEffect, useState } from "react";
import Cards from "react-credit-cards";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import { Button } from "@mui/material";
import { Focused } from "react-credit-cards";

function PaymentCard() {
  const [cvc, setCvc] = useState("");
  const [expiry, setExpiry] = useState("");
  const [name, setName] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [number, setNumber] = useState("");
  const [focus, setFocus] = useState<Focused | undefined>(undefined);

  function onChangeCardNumber(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("number");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setNumber(onlyNums);
  }

  function onChangeName(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("name");
    const onlyLetters = event.target.value.replace(/[^a-z ]/g, "");
    setName(onlyLetters);
  }

  function onChangeCVC(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("cvc");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setCvc(onlyNums);
  }

  function onChangeMonth(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("expiry");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setMonth(onlyNums);
  }

  function onChangeYear(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("expiry");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setYear(onlyNums);
  }

  useEffect(() => {
    if (month && year) {
      setExpiry(month + "/" + year);
    }
  }, [month, year]);

  return (
    <div id="PaymentForm">
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="100vh"
      >
        <Box
          display="grid"
          flexDirection="column"
          gap={3}
          p={5}
          border={1}
          borderRadius={2}
          borderColor="grey.300"
          boxShadow={3}
          width={300}
        >
          <Typography variant="h6" textAlign="center">
            Payment Form
          </Typography>
          <Cards
            cvc={cvc}
            expiry={expiry}
            focused={focus}
            name={name}
            number={number}
          />
          <TextField
            id="outlined-basic"
            label="Card Number"
            variant="outlined"
            value={number}
            slotProps={{
              input: {
                inputProps: {
                  inputMode: "numeric",
                  pattern: "\\d+",
                  maxLength: 16,
                },
              },
            }}
            sx={{ width: "100%" }}
            onChange={onChangeCardNumber}
          />
          <TextField
            id="outlined-basic"
            label="Name"
            value={name}
            variant="outlined"
            sx={{ width: "100%" }}
            onChange={onChangeName}
          />
          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              gap: 1,
              maxWidth: "100%",
            }}
          >
            <TextField
              label="Month"
              onChange={onChangeMonth}
              value={month}
              slotProps={{
                input: {
                  inputProps: {
                    inputMode: "numeric",
                    maxLength: 2,
                  },
                },
              }}
              sx={{ width: "50%" }}
            />
            <Box sx={{ fontSize: 24, userSelect: "none" }}>/</Box>
            <TextField
              label="Year"
              onChange={onChangeYear}
              value={year}
              slotProps={{
                input: {
                  inputProps: { maxLength: 2, inputMode: "numeric" },
                },
              }}
              sx={{ width: "50%" }}
            />
          </Box>
          <TextField
            id="outlined-basic"
            label="CVC-CVV"
            variant="outlined"
            value={cvc}
            sx={{ width: "100%" }}
            slotProps={{
              input: {
                inputProps: {
                  inputMode: "numeric",
                  maxLength: 3,
                },
              },
            }}
            onChange={onChangeCVC}
          />
          <Button variant="contained" sx={{ width: "100%" }}>
            Pay Now
          </Button>
        </Box>
      </Box>
    </div>
  );
}

export default PaymentCard;
