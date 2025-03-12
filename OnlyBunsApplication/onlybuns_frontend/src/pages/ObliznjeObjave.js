import React, { useEffect, useState } from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
import FavoriteIcon from '@mui/icons-material/Favorite';
import {Grid, Paper, IconButton } from '@mui/material';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import { useNavigate } from 'react-router-dom';
import { Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";

const ObliznjeObjave = () => {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [role, setRole] = useState('');
  const token = localStorage.getItem('jwtToken'); // Get JWT token from localStorage
  const [openDialog, setOpenDialog] = useState(false);
  const [dialogMessage, setDialogMessage] = useState('');
  const navigate = useNavigate(); // React Router's navigate function to redirect

  const handleCloseDialog = () => {
    setOpenDialog(false);
    navigate('/prijava');
  };

    const logout = () => {
      localStorage.removeItem("jwtToken"); // Remove token
  
      // Redirect to login page
      window.location.href = "/prijava";  // or use `useNavigate` from React Router v6
    };

  // Function to decode the JWT token by calling the backend endpoint
  useEffect(() => {
    if (!token) {
      setDialogMessage('No user found. Please log in.');
      setOpenDialog(true);
      setTimeout(() => {
        navigate('/prijava'); // Redirect to login after 15 seconds
      }, 15000); // Delay redirection to allow user to read the message
      return;
    }

    fetch('http://localhost:8080/auth/decodeJwt', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: token,
    })
      .then(response => response.json())
      .then(data => {
        if (data) {
          setEmail(data.Email);
          setUsername(data.Username);
          setRole(data.Role);
        }
      })
      .catch(error => {
        console.error('Error decoding JWT token:', error);
      });
  }, [token, navigate]);


  const [userLocation, setUserLocation] = useState(null);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
      // Prvo dohvati korisnikovu lokaciju
      axios.get(`http://localhost:8080/registrovaniKorisnik/lokacija/${username}`)
          .then(response => {
              const { latitude, longitude } = response.data;
              setUserLocation({ lat: latitude, lon: longitude });

              // Nakon što dobijemo lokaciju, dohvatamo objave u blizini
              return axios.get("http://localhost:8080/api/posts/nearby", {
                  params: { lat: latitude, lon: longitude }
              });
          })
          .then(response => setPosts(response.data))
          .catch(error => console.error("Error:", error));
  }, []);

  return (
    <div>
      {/* Dialog box for showing the message */}
      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>Notification</DialogTitle>
        <DialogContent>{dialogMessage}</DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary">
            OK
          </Button>
        </DialogActions>
      </Dialog>
      <AppBar position="static" sx={{ bgcolor: '#b4a7d6' }}>
        <Toolbar sx={{ justifyContent: 'space-between' }}>
          <Link to="/" style={{ textDecoration: 'none', display: 'flex', alignItems: 'center', color: 'inherit' }}>
            <Box sx={{ display: 'flex', alignItems: 'center', ml: 2 }}>
              <img src={logo} alt="OnlyBuns Logo" style={{ height: '40px', marginRight: '10px' }} />
              <Typography variant="h5" component="div" sx={{ fontWeight: 'bold' }}>
                OnlyBuns
              </Typography>
            </Box>
          </Link>
          <Box sx={{ display: 'flex', gap: 2 }}>
            <Button component={Link} to="/prijavljeniKorisnikPregled" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Feed
            </Button>
            <Button component={Link} to="/novaObjava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              New post
            </Button>
            <Button component={Link} to="/shop" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Trends
            </Button>
            <Button component={Link} to="/about" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Nearby Posts
            </Button>
            <Button component={Link} to="/contact" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Chat
            </Button>
            <Button onClick={logout} color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Logout
            </Button>
          </Box>
          <Box sx={{ display: 'flex', gap: 2, mr: 2, alignItems: 'center' }}>
            {token && username ? ( 
              <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                Welcome, {username}
              </Typography>
            ) : (
              <>
                <Button component={Link} to="/registracija" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
                  Sign In
                </Button>
                <Button component={Link} to="/prijava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
                  Log In
                </Button>
              </>
            )}
          </Box>
        </Toolbar>
      </AppBar>
      <div>
            <h2>Nearby posts on map</h2>
            {userLocation ? (
                <MapContainer center={[userLocation.lat, userLocation.lon]} zoom={13} style={{ height: "500px", width: "100%" }}>
                    <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
                    {/* Marker za korisnikovu lokaciju */}
                    <Marker position={[userLocation.lat, userLocation.lon]}>
                        <Popup>Vaša lokacija</Popup>
                    </Marker>
                    {/* Markeri za obližnje objave */}
                    {posts.map((post, index) => (
                        <Marker key={index} position={[post.latitude, post.longitude]}>
                            <Popup>
                                <b>{post.title}</b> <br />
                                {post.description}
                            </Popup>
                        </Marker>
                    ))}
                </MapContainer>
            ) : (
                <p>Loading map...</p>
            )}
        </div>
        

    </div>
  );
};

export default ObliznjeObjave;
