import { User } from 'lucide-react';
import { me } from '../API/services';
import type { DTOUser } from '../API/domain';
import { useEffect, useState } from 'react';


const UserProfile: React.FC = () => {

    const [user, setUser] = useState<DTOUser | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchUser = async () => {
      try {
        const userData = await me();
        setUser(userData);
      } catch (err) {
        setError('Failed to fetch user');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;
    if (!user) return <p>No user data</p>;

    return (
        <div className="flex items-center space-x-4">
            <div className="w-12 h-12 bg-green-600 rounded-full flex items-center justify-center">
                <User className="text-white" />
            </div>
            <div>
                <p className="text-sm font-medium">{user.firstName} {user.lastName}</p>
                <p className="text-xs text-gray-400">Logged in</p>
            </div>
        </div>
    );
};
export default UserProfile;
