import { Box, Paper, Typography } from "@mui/material";
import CancelIcon from "@mui/icons-material/Cancel";
import { PaymentFailedMessage } from "./types";

export default function PaymentFailed(failReason: PaymentFailedMessage) {
  function getErrorMessage(obj: PaymentFailedMessage): any[] {
    let errorMessage: object[] = [];
    if (obj.amount) {
      errorMessage.push(<p>Amount: {obj.amount}</p>);
    }
    if (obj.cvc) {
      errorMessage.push(<p>CVC: {obj.cvc}</p>);
    }
    if (obj.month) {
      errorMessage.push(<p>Month: {obj.month}</p>);
    }
    if (obj.name) {
      errorMessage.push(<p>Name: {obj.name}</p>);
    }
    if (obj.number) {
      errorMessage.push(<p>Number: {obj.number}</p>);
    }
    if (obj.year) {
      errorMessage.push(<p>Year: {obj.year}</p>);
    }
    return errorMessage;
  }

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f5f5f5",
      }}
    >
      <Paper
        elevation={3}
        sx={{
          p: 6,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          borderRadius: 3,
        }}
      >
        <CancelIcon sx={{ fontSize: 80, color: "red", mb: 2 }} />
        <Typography variant="h4" component="h1" gutterBottom>
          Payment Failed
        </Typography>
        <Typography variant="body1" color="textSecondary">
          Failure reason: {getErrorMessage(failReason)} Please try again!
        </Typography>
      </Paper>
    </Box>
  );
}
