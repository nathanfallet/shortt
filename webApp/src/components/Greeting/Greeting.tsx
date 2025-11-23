import './Greeting.css';

import type {AnimationEvent} from 'react';
import {useState} from 'react';
import {Greeting as KotlinGreeting} from 'shared';

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
