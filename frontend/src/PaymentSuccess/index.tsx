import { Box, Paper, Typography } from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

export default function PaymentSuccess() {
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
        <CheckCircleIcon sx={{ fontSize: 80, color: "green", mb: 2 }} />
        <Typography variant="h4" component="h1" gutterBottom>
          Payment Successful
        </Typography>
        <Typography variant="body1" color="textSecondary">
          Thank you, for your payment!
        </Typography>
      </Paper>
    </Box>
  );
}
