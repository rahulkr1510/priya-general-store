import { useEffect, useState } from "react";

function App() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const [email, setEmail] = useState("rahul@test.com");
  const [password, setPassword] = useState("12345");

  useEffect(() => {
    fetch("http://13.218.245.88:8080/products")
      .then((res) => res.json())
      .then((data) => setProducts(data));
  }, []);

  const login = async () => {
    const response = await fetch("http://13.218.245.88:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    const result = await response.text();

    if (result === "Login successful") {
      setIsLoggedIn(true);
      alert("Login successful");
    } else {
      alert("Invalid email or password");
    }
  };

  const addToCart = (product) => {
    setCart([...cart, product]);
  };

  const totalAmount = cart.reduce((total, item) => total + item.price, 0);

  const placeOrder = async () => {
    if (cart.length === 0) {
      alert("Cart is empty");
      return;
    }

    for (const item of cart) {
      await fetch("http://13.218.245.88:8080/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: Date.now() + item.id,
          userId: 1,
          productId: item.id,
          quantity: 1,
        }),
      });
    }

    alert("Order placed successfully");
  };

  const makePayment = async () => {
    if (cart.length === 0) {
      alert("No items to pay");
      return;
    }

    await fetch("http://13.218.245.88:8080/payments", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: Date.now(),
        orderId: 1,
        amount: totalAmount,
        paymentMode: "UPI",
      }),
    });

    alert("Payment successful");
    setCart([]);
  };

  if (!isLoggedIn) {
    return (
      <div style={{ padding: "30px", textAlign: "center" }}>
        <h1>Priya General Store Login</h1>

        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <br /><br />

        <input
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <br /><br />

        <button onClick={login}>Login</button>
      </div>
    );
  }

  return (
    <div style={{ padding: "20px", textAlign: "center" }}>
      <h1>🛒 Priya General Store</h1>

      <button onClick={() => setIsLoggedIn(false)}>Logout</button>

      <h2>Products</h2>

      {products.map((p) => (
        <div
          key={p.id}
          style={{
            border: "1px solid black",
            padding: "15px",
            margin: "10px",
          }}
        >
          <h3>{p.name}</h3>
          <p>Price: ₹{p.price}</p>
          <button onClick={() => addToCart(p)}>Add to Cart</button>
        </div>
      ))}

      <hr />

      <h2>Cart</h2>

      {cart.length === 0 ? (
        <p>No items in cart</p>
      ) : (
        <>
          {cart.map((item, index) => (
            <p key={index}>
              {item.name} - ₹{item.price}
            </p>
          ))}

          <h3>Total: ₹{totalAmount}</h3>

          <button onClick={placeOrder}>Place Order</button>
          <br /><br />
          <button onClick={makePayment}>Pay Now UPI</button>
        </>
      )}
    </div>
  );
}

export default App;