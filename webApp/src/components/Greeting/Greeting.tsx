import './Greeting.css';

import type {AnimationEvent} from 'react';
import {useState} from 'react';
import {Greeting as KotlinGreeting, User} from 'shared';

export const Greeting = () => {
    const greeting = new KotlinGreeting();
    const [isVisible, setIsVisible] = useState<boolean>(false);
    const [isAnimating, setIsAnimating] = useState<boolean>(false);

    const handleClick = () => {
        if (isVisible) {
            setIsAnimating(true);
        } else {
            setIsVisible(true);
        }

        const user = new User('123e4567-e89b-12d3-a456-426614174000');
        console.log(user);

        const user2 = {
            id: '987e6543-e21b-12d3-a456-426614174000',
        }
        console.log(user2);
    };

    const handleAnimationEnd = (event: AnimationEvent<HTMLDivElement>) => {
        if (event.animationName === 'fadeOut') {
            setIsVisible(false);
            setIsAnimating(false);
        }
    };

    return (
        <div className="greeting-container">
            <button onClick={handleClick} className="greeting-button">
                Click me!
            </button>

            {isVisible && (
                <div className={isAnimating ? 'greeting-content fade-out' : 'greeting-content'}
                     onAnimationEnd={handleAnimationEnd}>
                    <div>React: {greeting.greet()}</div>
                </div>
            )}
        </div>
    );
}
