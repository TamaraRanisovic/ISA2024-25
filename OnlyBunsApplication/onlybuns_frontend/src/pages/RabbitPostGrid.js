import { Grid, Paper, Box, IconButton, Typography } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import { Link } from 'react-router-dom';

const RabbitPostGrid = ({ rabbitPosts }) => {
  return (
    <Grid container spacing={3} sx={{ mt: 4 }}>
      {rabbitPosts.map((post, index) => (
        <Grid item xs={12} sm={6} md={3} key={index}>
          {/* Link to Detailed View */}
          <Link to={`/objavaPrikaz/${post.id}`} style={{ textDecoration: 'none' }}>
            <Paper elevation={6} sx={{ padding: 3, borderRadius: '15px', textAlign: 'center', boxShadow: '0 12px 24px rgba(0, 0, 0, 0.1)' }}>
              <img
                src={`http://localhost:8080/images/${post.slika}`}
                alt={post.opis}
                style={{ height: '240px', width: '100%', borderRadius: '10px', marginBottom: '15px' }}
              />

              {/* Likes and Comments Row */}
              <Box sx={{ display: 'flex', justifyContent: 'center', gap: 1, alignItems: 'center', mb: 1 }}>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                  <IconButton sx={{ color: '#e91e63' }}>
                    <FavoriteIcon />
                  </IconButton> 
                  <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                    {post.broj_lajkova}
                  </Typography>
                </Box>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                  <IconButton color="secondary">
                    <ChatBubbleOutlineIcon />
                  </IconButton>
                  <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                    {post.broj_komentara}
                  </Typography>
                </Box>
              </Box>

              {/* Description Row */}
              <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                <Typography sx={{ fontWeight: 'bold' }}>
                  {post.korisnicko_ime}
                </Typography>
                <Typography variant="body2" color="textSecondary" sx={{ fontStyle: 'italic', whiteSpace: 'normal', wordWrap: 'break-word' }}>
                  {post.opis}
                </Typography>
              </Box>
            </Paper>
          </Link>
        </Grid>
      ))}
    </Grid>
  );
};

export default RabbitPostGrid;
