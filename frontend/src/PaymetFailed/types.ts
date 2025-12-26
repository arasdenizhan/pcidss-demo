export class PaymentFailedMessage {
  name: string;
  number: string;
  year: string;
  month: string;
  cvc: string;
  amount: string;

  constructor() {
    this.name = "";
    this.number = "";
    this.year = "";
    this.month = "";
    this.cvc = "";
    this.amount = "";
  }
}
