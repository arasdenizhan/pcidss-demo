import "react-credit-cards/es/styles-compiled.css";

import { ChangeEvent, useEffect, useState } from "react";
import Cards from "react-credit-cards";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import { Button } from "@mui/material";
import { Focused } from "react-credit-cards";
import { v4 as uuidv4 } from "uuid";
import PaymentSuccess from "../PaymentSuccess";
import PaymentFailed from "../PaymetFailed";
import { PaymentFailedMessage } from "../PaymetFailed/types";

function PaymentCard() {
  const AppUrl = process.env["REACT_APP_API_URL"]
    ? process.env["REACT_APP_API_URL"]
    : "https://localhost:8443";

  const PaymentUrl = process.env["REACT_APP_PAYMENT_API_URL"]
    ? process.env["REACT_APP_PAYMENT_API_URL"]
    : "/api/v1/payment";

  const [cvc, setCvc] = useState("");
  const [expiry, setExpiry] = useState("");
  const [name, setName] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [number, setNumber] = useState("");
  const [focus, setFocus] = useState<Focused | undefined>(undefined);
  const [paymentForm, setPaymentForm] = useState(true);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState(new PaymentFailedMessage());

  const [numberError, setNumberError] = useState(false);
  const [nameError, setNameError] = useState(false);
  const [monthError, setMonthError] = useState(false);
  const [yearError, setYearError] = useState(false);
  const [cvcError, setCvcError] = useState(false);

  function onChangeCardNumber(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("number");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setNumber(onlyNums);
    setNumberError(onlyNums.length != 16);
  }

  function onChangeName(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("name");
    const onlyLetters = event.target.value.replace(/[^a-z ]/g, "");
    setName(onlyLetters);
    if (!onlyLetters) {
      setNameError(true);
    } else {
      setNameError(false);
    }
  }

  function onChangeCVC(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("cvc");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setCvc(onlyNums);
    setCvcError(Number(onlyNums) < 100 || Number(onlyNums) > 999);
  }

  function onChangeMonth(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("expiry");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setMonth(onlyNums);
    setMonthError(Number(onlyNums) < 1 || Number(onlyNums) > 12);
  }

  function onChangeYear(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ): void {
    setFocus("expiry");
    const onlyNums = event.target.value.replace(/\D/g, "");
    setYear(onlyNums);
    setYearError(Number(onlyNums) < 25 || Number(onlyNums) > 40);
  }

  useEffect(() => {
    if (month && year) {
      setExpiry(month + "/" + year);
    }
  }, [month, year]);

  const makePayment = async () => {
    const requestBody = JSON.stringify({
      number: number,
      name: name,
      year: year,
      month: month,
      cvc: cvc,
      amount: 100, //default value
    });
    const res = await fetch(AppUrl + PaymentUrl + "/card", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Idempotency-Key": uuidv4(),
      },
      body: requestBody,
      credentials: "include",
    });

    if (!res.ok) {
      const messageWrapper = Object.assign(
        new PaymentFailedMessage(),
        await res.json()
      );
      console.log("Payment failed, reason = " + messageWrapper);
      setErrorMessage(messageWrapper);
      setError(true);
      setPaymentForm(false);
      setTimeout(() => {
        setCvc("");
        setName("");
        setMonth("");
        setExpiry("");
        setYear("");
        setNumber("");
        setFocus(undefined);
        setError(false);
        setPaymentForm(true);
      }, 3000);
      return;
    }

    setSuccess(true);
    setPaymentForm(false);
    setTimeout(() => {
      setCvc("");
      setName("");
      setMonth("");
      setExpiry("");
      setYear("");
      setNumber("");
      setFocus(undefined);
      setSuccess(false);
      setPaymentForm(true);
    }, 3000);
  };

  return (
    <div id="PaymentForm">
      {error ? PaymentFailed(errorMessage) : <></>}
      {success ? (
        <PaymentSuccess />
      ) : (
        paymentForm && (
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
                label="Payment Amount"
                variant="outlined"
                value={"100"}
                sx={{ width: "100%" }}
                disabled={true}
              />
              <TextField
                id="outlined-basic"
                label="Card Number"
                variant="outlined"
                error={numberError}
                helperText={
                  numberError ? "Please enter a valid card number" : ""
                }
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
                error={nameError}
                helperText={nameError ? "Please enter a valid name" : ""}
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
                  error={monthError}
                  helperText={monthError ? "Please enter a valid month" : ""}
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
                  error={yearError}
                  helperText={
                    yearError ? "Please enter a year between 25-40" : ""
                  }
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
                error={cvcError}
                helperText={cvcError ? "Please enter a valid cvc" : ""}
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
              <Button
                variant="contained"
                sx={{ width: "100%" }}
                onClick={makePayment}
                disabled={
                  nameError ||
                  cvcError ||
                  numberError ||
                  monthError ||
                  yearError
                }
              >
                Pay Now
              </Button>
            </Box>
          </Box>
        )
      )}
    </div>
  );
}

export default PaymentCard;
